/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


$(document).ready(function(){
    $('#filter_nav > li').hover(function() {
            $('.dropdown_menu', this).slideDown();
        }, function() {
            $('.dropdown_menu', this).slideUp();
    });
});
