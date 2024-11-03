function moneyCurrency(number){
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}
function changeVal(el){
    let qt = parseInt(el.parent().children(".qt").html());
    let price = parseFloat(el.parent().children(".price").html().replace(/[&nbsp;.]/g,""));
    let total_of_a_product = Math.round(price * qt * 100) / 100;
    el.parent().children(".total-price").html(moneyCurrency(total_of_a_product));
    changeTotal();
}
function changeTotal(){
    let currentTotalPrice = 0;
    $(".total-price").each(function(index){
        currentTotalPrice += parseFloat($(".total-price").eq(index).html().replace(/[&nbsp;.]/g,""));
    });
    $(".total span").html(moneyCurrency(currentTotalPrice));
}
$(document).ready(function(){
    $(".price").each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(moneyCurrency(tmp));
    });
    var total_tmp = 0;
    $(".total-price").each(function(){
        let tmp = parseFloat($(this).html());
        total_tmp += tmp;
        $(this).html(moneyCurrency(tmp));
    });
    $(".total span").html(moneyCurrency(total_tmp));
    $(".remove").click(function(){
        let el = $(this);
        el.parent().parent().addClass(".removed");
        window.setTimeout(function(){
            el.parent().parent().slideUp('fast', function(){
                el.parent().parent().remove();
                changeTotal();
            });
        }, 200);
    });
    $(".qt-plus").click(function(){
        $(this).parent().children(".qt").html(parseInt($(this).parent().children(".qt").html()) + 1);
        $(this).parent().children(".total-price").addClass("added");
        let el = $(this);
        window.setTimeout(function(){
            el.parent().children(".total-price").removeClass("added");
            changeVal(el);
        }, 150);
    });
    $(".qt-minus").click(function(){
        var child = $(this).parent().children(".qt");
        if(parseInt(child.html()) > 1){
            child.html(parseInt(child.html()) - 1);
        }
        $(this).parent().children(".total-price").addClass("minused");
        let el = $(this);
        window.setTimeout(function(){
            el.parent().children(".total-price").removeClass("minused");
            changeVal(el);
        }, 150);
    });
    setTimeout(function(){
        $(".note-text").fadeOut(); 
    }, 5000);
});


