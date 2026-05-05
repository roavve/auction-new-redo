$(document).ready(function () {

    /* var hideAuctionStart=document.getElementById("hideAuctionStart");
     var hideAuctionEndt=document.getElementById("hideAuctionEnd");

     var auctionStartDate=document.getElementById("auctionStartDate");
     var auctionEndDate=document.getElementById("auctionEndDate");

     var hideAuctionStartTime=document.getElementById("hideAuctionStartTime");
     var hideAuctionEndTime=document.getElementById("hideAuctionEndTime");

     auctionStartDate.value=hideAuctionStart.value.substring(0,10) + ' ' + hideAuctionStartTime.value;
     auctionEndDate.value=hideAuctionEndt.value.substring(0,10) + ' ' + hideAuctionEndTime.value; */

    var checkDiv = $(".check");
    $(".reg-condition").click(function (event) {
        if (checkDiv.hasClass('active')) {
            checkDiv.removeClass('active');
            $(".accept").attr('disabled', 'disabled');
        } else {
            checkDiv.addClass('active');
            $(".accept").attr('disabled', 'enabled');
        }
    });

    $(".accept").click(function (event) {
        var recordKey = $(this).data("reckey");
        if (checkDiv.hasClass('active')){
            if (confirm('დარწმუნებული ხართ რომ ეთანხმებით მოწვევას ?')) {
                $.ajax({
                    type: "POST",
                    url: "/auction/auction/auction_accept?recordKey=" + recordKey,
                    async: false,
                    cache: false,
                    success: function (data) {
                        window.location.href = "/auction/user/client_home";
                    }
                });
            }
        }
        else{
            alert('ფასთა გამოკითხვაში მონაწილეობის მისაღებად აუცილებელია დაეთანხმოთ მითითებულ პირობებს');
        }
    });

    $(".reject").click(function (event, response) {
        var recordKey = $(this).data("reckey");
        if (confirm('დარწმუნებული ხართ რომ უარყოფთ მოწვევას ?')) {
            $.ajax({
                type: "POST",
                url: "/auction/auction/auction_reject?recordKey=" + recordKey,
                async: false,
                cache: false,
                success: function (data) {
                    window.location.href = "/auction/user/client_home";
                }
            });
        }
    });

});