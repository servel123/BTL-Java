function moneyCurrency(number){
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}
function changeVal(el){
    let qt = parseInt(el.parent().children(".qt").html());
    let price = parseFloat(el.parent().children(".price").html().replace(/[&nbsp;.]/g,""));
    let total_of_a_product = Math.round(price * qt * 100) / 100;
    el.parent().children(".total-price").html(moneyCurrency(total_of_a_product));
}
$(document).ready(function(){
    $(".price").each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(moneyCurrency(tmp));
    });
    $(".total-price").each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(moneyCurrency(tmp));
    });
    $(".remove").click(function(){
        let el = $(this);
        el.parent().parent().addClass(".removed");
        window.setTimeout(function(){
            el.parent().parent().hide('fast', function(){
                el.parent().parent().remove();
            });
        }, 200);
    });
    $(".qt-plus").click(function(){
        let qtUpdate = parseInt($(this).parent().children(".qt").html());
        $(this).parent().children(".qt").html(qtUpdate + 1);
        $(this).parent().children(".total-price").addClass("added");
        let el = $(this);
        window.setTimeout(function(){
            el.parent().children(".total-price").removeClass("added");
            changeVal(el);
            el.parent().find("input[name='quantity']").val(qtUpdate + 1);
            el.parent().find("form").submit();
        }, 150);
    });
    $(".qt-minus").click(function(){
        let qtUpdate = parseInt($(this).parent().children(".qt").html());
        if(qtUpdate > 1){
            $(this).parent().children(".qt").html(qtUpdate - 1);
        }
        $(this).parent().children(".total-price").addClass("minused");
        let el = $(this);
        window.setTimeout(function(){
            el.parent().children(".total-price").removeClass("minused");
            changeVal(el);
            el.parent().find("input[name='quantity']").val(qtUpdate - 1);
            el.parent().find("form").submit();
        }, 150);
    });
});


