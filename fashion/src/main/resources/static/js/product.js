/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
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
    let pop_pant_left = $('#pop_relatedP_left');
    let pop_pant_right = $('#pop_relatedP_right');
    let displayPant = $('.displayPart.relatedP').first();
    pop_pant_left.on('click', function(){
        displayPant.scrollLeft(displayPant.scrollLeft() - 500);
    });
    pop_pant_right.on('click', function(){
        displayPant.scrollLeft(displayPant.scrollLeft() + 500);
    });
    setTimeout(function(){
        $(".note-text").fadeOut(); 
    }, 5000);
});