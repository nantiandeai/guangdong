package com.creatoo.hn.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

public class PartUpload  implements Runnable {
        
	private MultipartFile file;
	private long startPos;
	private long partSize;
	private int partNumber;
	private String uploadId;
	private String key;
	private OSSClient client;
	private List<PartETag> partETags;

	public PartUpload(List<PartETag> partETags, OSSClient client, String key, MultipartFile file, long startPos, long partSize, int partNumber, String uploadId) {
			this.file = file;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
            this.key = key;
            this.client = client;
            this.partETags = partETags;
        }

	@Override
	public void run() {
		InputStream bistream = null;
		try {
			bistream = file.getInputStream();
			bistream.skip(this.startPos);

			UploadPartRequest uploadPartRequest = new UploadPartRequest();
			uploadPartRequest.setBucketName(AliyunOssUtil.getBucketName());
			uploadPartRequest.setKey(key);
			uploadPartRequest.setUploadId(this.uploadId);
			uploadPartRequest.setInputStream(bistream);
			uploadPartRequest.setPartSize(this.partSize);
			uploadPartRequest.setPartNumber(this.partNumber);

			UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
			System.out.println("Part#" + this.partNumber + " done\n");
			synchronized (partETags) {
				partETags.add(uploadPartResult.getPartETag());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bistream != null) {
				try {
					bistream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
