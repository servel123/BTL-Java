function changeCurrency(number){
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}
$(document).ready(function(){
    let tmp = parseFloat($('#product_price').html());
    $('#product_price').html(changeCurrency(tmp));
    $('.related_product_price').each(function(){
       let val = parseFloat($(this).html());
       $(this).html(changeCurrency(val));
    });
    let pop_left = $('#pop_relatedP_left');
    let pop_right = $('#pop_relatedP_right');
    let displayPant = $('.displayPart.relatedP').first();
    pop_left.on('click', function(){
        displayPant.scrollLeft(displayPant.scrollLeft() - 500);
    });
    pop_right.on('click', function(){
        displayPant.scrollLeft(displayPant.scrollLeft() + 500);
    });
    $('.total').each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(changeCurrency(tmp));
    });
});