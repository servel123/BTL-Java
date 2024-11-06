function changeCurrency(number){
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}
$(document).ready(function(){
    $(".price").each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(changeCurrency(tmp));
    });
    $(".total-price").each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(changeCurrency(tmp));
    });
    let tmp = parseFloat($("#overall").html());
    $("#overall").html(changeCurrency(tmp));
});