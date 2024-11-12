function currency(number){
    return number.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}
$(document).ready(function(){
    $('.total').each(function(){
        let tmp = parseFloat($(this).html());
        $(this).html(currency(tmp));
    });
//    Edit button
    $('.btn-edit').on('click', function(){
       $(this).closest('td').find('.editModal').show();
    });
//    Cancel button
    $('.btn-cancel').on('click', function(){
       $(this).closest('.modal').hide();
    });
//    Delete button
    $('.btn-delete').on('click', function(){
       $(this).closest('td').find('.confirmModal').show();
    });
//    Add button
    $('.btn-add').on('click', function(){
        $(this).closest('.main-title').find('.addModal').show();
    });
    $('.general-table td:not(:has(button))').click(function() {
       if($(this).hasClass('expanded')){
           $(this).removeClass('expanded');  
       } else{
           $('.general-table td.expanded').removeClass('expanded');
           $(this).addClass('expanded');
       }
    });
});