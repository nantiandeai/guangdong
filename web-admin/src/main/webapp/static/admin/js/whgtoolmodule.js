/**
 * 扩展处理帮助
 * Created by qxkun on 2017/3/18.
 */

;(function($, window){

    function getJsData(typesFn){

        if (!typesFn || typeof typesFn != 'string') return [];

        if (/^\[.*\]$/.test(typesFn.replace(/\n/g, ''))){
            try {
                return $.parseJSON(typesFn);
            } catch (e) {
                return [];
            }
        }

        var medthod = window;
        if (/\./.test(typesFn)){
            var ffs = typesFn.split('.');
            for(var i in ffs){
                if (ffs[i]){
                    medthod = medthod[ffs[i]];
                }
            }
        }else{
            medthod = medthod[typesFn];
        }
        if (medthod !== window && typeof medthod == 'function'){
            return medthod.call(null) || [];
        }
    }

    function showTypes(el, name, value, typesFn, inputType, textVal) {
        var inputType = inputType || 'checkbox';
        var item_input = '<input type="'+inputType+'"/>';
        var item_label = '<label></label>';
        var inputIdPre = (name || Math.random()*1000)+'-'+inputType;

        //处理调用取选项的方法
        var types = getJsData(typesFn);

        //有设值选中的处理
        var valueArr = [];
        if (value && inputType=='checkbox'){
            var vvv = value.split(/\s*,\s*/);
            for(var i in vvv){
                valueArr.push(vvv[i]);
            }
        }else{
            value? valueArr.push(value):'';
        }
        //console.info(valueArr);

        for(var i in types){
            var id = (textVal)? types[i].text : types[i].id;
            var text = types[i].text;
            var itemInput = $(item_input);
            var itemLabel = $(item_label);
            var __id = inputIdPre+'-'+id;

            var inputArrt = {
                id: __id,
                name: name,
                value: id
            };
            //处理设置项选中
            if (valueArr.length>0 && (valueArr.toString().indexOf(id)!=-1) ){
                inputArrt.checked = 'checked';
            }else if (inputType=='radio' && i==0){
                inputArrt.checked = 'checked';
            }
            el.append(itemInput);
            el.append(itemLabel);
            itemInput.attr(inputArrt);
            if ('checkbox'==inputType){
                itemInput.addClass('styled');
            }
            itemLabel.attr('for', __id).text(text);
        }
    }

    $(function(){
        $(".whg-js-data").each(function(){
            var el = $(this);
            var typesFn = el.attr('js-data');
            //var inputType = el.attr('js-type');
            var name = el.attr('name');
            var value = el.attr('value');
            var cls = el.attr("class");
            var inputType = cls.indexOf('radio')!=-1? 'radio' : 'checkbox';
            var textVal = el.attr("textVal")? true: false;

            showTypes(el, name, value, typesFn, inputType, textVal)
        });
    });

})(jQuery, window);