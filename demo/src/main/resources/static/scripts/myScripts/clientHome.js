$(document).ready(function () {

    var socket = new SockJS('/auction/websock');
    stompClient = Stomp.over(socket);


    stompClient.connect( {}, function (frame) {

        // console.log('connecting...');

        stompClient.subscribe('/topic/send', function (data) {


            var obj = JSON.parse(data.body);


            var bidError = obj.bidError;

            if (bidError > 0) {
                updateData(obj);
            }

            else {
                showError(obj);
            }
        });
    });


    function updateData(obj) {


        var recordKey = obj.recordKey;
        var userId = obj.userId;
        var auctionStepKey = obj.auctionStepKey;
        var auctionTypeKey = obj.auctionTypeKey;
        var myMinBidValue = obj.myMinBidVal.toFixed(2);

        var myMaxBidValue = obj.myMaxBidVal.toFixed(2);
        var maxBidValue = obj.maxBidValue.toFixed(2);


        var minBidValue = obj.minBidValue.toFixed(2);
        var bidEndDateStr = obj.bidEndDateStr;
        var timeLeft = obj.timeLeft;
        var lastBidTimeStr = obj.lastBidTimeStr;

        var isLastBidMine = 0;

        if (userId == document.getElementById(recordKey + "-companyUser").value) {
            isLastBidMine = 1;
        }

        var rowLastBid = recordKey + "-LastBidVal";
        var rowLastBidTime = recordKey + "-LastBidDate";

        var rowInputBid = recordKey + "-bidValue";

        var updatedBidEndDate = obj.bidEndDate;
        var updatedBidEndTime = obj.bidEndTime;


        if (auctionStepKey == 'key.auctionStep.offer') { // პირველი ეტაპი


            if (isLastBidMine == 1) {


                if (auctionTypeKey == 'key.auctionType.buy') {

                    // console.log('buy');
                    document.getElementById(rowLastBid).innerHTML = myMinBidValue;

                } else if (auctionTypeKey == 'key.auctionType.sell') {

                    // console.log('sell');
                    document.getElementById(rowLastBid).innerHTML = myMaxBidValue;
                }

                document.getElementById(rowLastBid).style.color = "green";


                document.getElementById(rowLastBidTime).innerHTML = lastBidTimeStr;
                document.getElementById(rowInputBid).value = null;

            }


        }
        else if (auctionStepKey == 'key.auctionStep.bid') {// მეორე ეტაპი


            // console.log('old bid end date: ' + document.getElementById(""+recordKey+"-elapsed-time").dataset.bidenddate);
            document.getElementById(""+recordKey+"-elapsed-time").dataset.bidenddate = updatedBidEndDate;
            // console.log('new bid end date: ' + document.getElementById(""+recordKey+"-elapsed-time").dataset.bidenddate);


            // console.log('old bid end time: ' + document.getElementById(""+recordKey+"-elapsed-time").dataset.bidendtime);
            document.getElementById(""+recordKey+"-elapsed-time").dataset.bidendtime = updatedBidEndTime;
            // console.log('new bid end time: ' + document.getElementById(""+recordKey+"-elapsed-time").dataset.bidendtime);

            if (auctionTypeKey == 'key.auctionType.buy') {

                // console.log('buy');
                document.getElementById(rowLastBid).innerHTML = minBidValue;

            } else if (auctionTypeKey == 'key.auctionType.sell') {

                // console.log('sell');
                document.getElementById(rowLastBid).innerHTML = maxBidValue;
            }


            if (isLastBidMine == 1)
                document.getElementById(rowLastBid).style.color = "green";
            else
                document.getElementById(rowLastBid).style.color = "red";

            document.getElementById(rowLastBidTime).innerHTML = lastBidTimeStr;
            document.getElementById(rowInputBid).value = null;


        }
        else {


            document.getElementById(rowLastBid).innerHTML = '0';
            document.getElementById(rowLastBid).style.color = "black";

            document.getElementById(rowLastBidTime).innerHTML = lastBidTimeStr;
            document.getElementById(rowInputBid).value = null;

        }

    }

    function showError(obj) {

        var bidError = obj.bidError;
        var userId = obj.userId;
        var startBidBValue = obj.startBidBValue;
        var maxBidBValue = obj.maxBidBValue;
        var recordKey = obj.recordKey;
        var rowInputBid = recordKey + "-bidValue";
        var errorString = "";



        document.getElementById(rowInputBid).value = null;


        if (bidError == -1) {
            errorString = "შემოტანილი ბიდი უნდა იყოს ბიჯის  ჯერადი!";
        }

        if (bidError == -2) {
            errorString = "შემოტანილი ბიდი ნაკლები  უნდა იყოს ბოლო ბიდზე !";
        }


        if (bidError == -5) {
            errorString = "შემოტანილი ბიდი ნაკლები არ უნდა იყოს  " + startBidBValue + "-ზე !";
        }

        if (bidError == -6) {
            errorString = "შემოტანილი ბიდი მეტი უნდა  იყოს  " + startBidBValue + "-ზე !";
        }

        if (bidError == -7) {
            errorString = "შემოტანილი ბიდი მეტი  უნდა იყოს ბოლო ბიდზე !";
        }

        if (bidError == -8) {
            errorString = "შემოტანილი ბიდი უნდა იყოს ბიჯის  ჯერადი!";
        }

        if (bidError == -9) {
            errorString = "შემოტანილი ბიდი მეტი უნდა  იყოს " + startBidBValue + "-ზე !";
        }

        if (bidError == -11) {
            errorString = "გთხოვთ დაელოდოთ აუქციონის დაწყებას!";
        }

        if (bidError == -12) {
            errorString = "აუქციონის დრო ამოიწურა!";
        }

        if (bidError == -13) {
            errorString = "აუქციონი არ არის არცერთ ეტაპზე!";
        }

        if (bidError == -15) {
            errorString = "შემოტანილი ბიდი ნაკლები უნდა იყოს " + maxBidBValue + "-ზე !";
        }

        if (bidError == -16) {
            errorString = "თქვენ არ მიგიღიათ მონაწილეობა დახურულ ფასთა გამოკითხვაში!";
        }


        if (userId == document.getElementById(recordKey + "-companyUser").value)
            showCustomDialog(errorString);
    }

    function showCustomDialog(errorString) {
        swal({
            title: "",
            text: errorString,
            icon: "warning",
            button: "Ok",
            dangerMode: true,
        });
    }


    setInterval(function () {

        $.ajax({
            url: '/auction/auction/ajax/auction_data',
            datatype: "json",
            success: function (data) {

                let tblBody = $("#active_auction_table_body_id");
                let tbl = $("#active_auction_tableid");
                let htm = "";

                // console.log(data);

                for (i = 0; i < data.list.length; i++) {

                    var myMinBidValue = data.list[i].myMinBidVal.toFixed(2);
                    var myMaxBidValue = data.list[i].myMaxBidVal.toFixed(2);
                    var maxBidValue = data.list[i].maxBidValue.toFixed(2);
                    var minBidValue = data.list[i].minBidValue.toFixed(2);
                    var bidEndDateStr = data.list[i].bidEndDateStr;
                    // var bidEndDate = data.list[i].auctionEndDate;


                    var bidEndTimeStr = data.list[i].auctionEndTime;
                    var auctionStatus = data.list[i].auctionStatus;
                    var auctionStep = data.list[i].auctionStep;
                    var isLastBidMine = data.list[i].isLastBidMine;
                    var auctionTypeKey = data.list[i].auctionTypeKey;
                    var lastBidDate = data.list[i].lastBidDate;
                    var timeLeft = data.list[i].timeLeft;
                    var auctionStepKey = data.list[i].auctionStepKey;
                    //	auctionStatus = auctionStatus + ' ('+auctionStep+')';
                    //	var auctionStatusKey = data.list[i].auctionStatusKey;
                    //	var auctionEndTimeStr = data.list[i].auctionEndTimeStr;


                    var recordKey = data.list[i].auctionId;

                    // console.log('recordKey: ' + recordKey);

                    var rowLastBid = recordKey + "-LastBidVal";
                    var rowLastBidDate = recordKey + "-LastBidDate";
                    var rowBidEndDate = recordKey + "-BidEndDate";
                    var rowBidEndTime = recordKey + "-BidEndTime";
                    var rowTimeLeft = recordKey + "-TimeLeft";
                    var rowStatus = recordKey + "-Status";
                    var rowStep = recordKey + "-Step";
                    var rowStep1 = recordKey + "-auction-step";


                    if (auctionStepKey == 'key.auctionStep.offer') {

                        if (auctionTypeKey == 'key.auctionType.buy') {
                            document.getElementById(rowLastBid).innerHTML = myMinBidValue;
                        }
                        else if (auctionTypeKey == 'key.auctionType.sell') {
                            document.getElementById(rowLastBid).innerHTML = myMaxBidValue;
                        }

                        document.getElementById(rowLastBid).style.color = "green";

                    }
                    else if (auctionStepKey == 'key.auctionStep.bid') {

                        if (auctionTypeKey == 'key.auctionType.buy') {
                            document.getElementById(rowLastBid).innerHTML = minBidValue;

                        }
                        else if (auctionTypeKey == 'key.auctionType.sell') {
                            document.getElementById(rowLastBid).innerHTML = maxBidValue;
                        }


                        if (isLastBidMine == 1) {
                            document.getElementById(rowLastBid).style.color = "green";
                        }
                        else {
                            document.getElementById(rowLastBid).style.color = "red";
                        }

                    }
                    else {

                        if (new Date() < new Date(data.list[i].auctionStartDate)){
                            auctionStep = 'საკითხების დაზუსტება';
                        }
                        // document.getElementById(rowLastBidTime).innerHTML = lastBidTimeStr; /* recordKey-rowLastBidTime ელემენტი არ არის გაწერილი */
                        // document.getElementById(rowLastBidDate).innerHTML = lastBidDate;
                        document.getElementById(rowLastBid).style.color = "black";

                    }


                    document.getElementById(rowLastBidDate).innerHTML = lastBidDate;
                    // document.getElementById(rowTimeLeft).innerHTML = timeLeft;
                    document.getElementById(rowStatus).innerHTML = auctionStatus;
                    // document.getElementById(rowStep).innerHTML = "(" + auctionStep + ")";
                    document.getElementById(rowStep1).innerHTML = "" + auctionStep + "";
                    document.getElementById(rowBidEndDate).innerHTML = bidEndDateStr + " " + '<span id=' + rowBidEndDate + '>' + bidEndTimeStr + '</span>';

                }
            }
        });
    }, 10000);


    var hideAuctionStartHome = document.getElementById("hideAuctionStartHome");
    var hideAuctionEndtHome = document.getElementById("hideAuctionEndHome");


    var auctionStartDateHome = document.getElementById("auctionStartDateHome");
    var auctionEndDateHome = document.getElementById("auctionEndDateHome");


    var hideAuctionStartTimeHome = document.getElementById("hideAuctionStartTimeHome");
    var hideAuctionEndTimeHome = document.getElementById("hideAuctionEndTimeHome");

    var hideAuctionStartHomeActive = document.getElementById("hideAuctionStartHomeActive");
    var hideAuctionEndtHomeActive = document.getElementById("hideAuctionEndHomeActive");


    var auctionStartDateHomeActive = document.getElementById("auctionStartDateHomeActive");
    var auctionEndDateHomeActive = document.getElementById("auctionEndDateHomeActive");


    var hideAuctionStartTimeHomeActive = document.getElementById("hideAuctionStartTimeHomeActive");
    var hideAuctionEndTimeHomeActive = document.getElementById("hideAuctionEndTimeHomeActive");


    $('#auctionStartDateHomeActive').html(hideAuctionStartHomeActive.value.substring(0, 10) + ' ' + hideAuctionStartTimeHomeActive.value);
    $('#auctioEndDateHomeActive').html(hideAuctionEndtHomeActive.value.substring(0, 10) + ' ' + hideAuctionEndTimeHomeActive.value);


//	   var hideInvited=document.getElementById("hideInvited").value.substring(0,10);
//	   $('#auctionHideInvited').html(hideInvited);


//	   var hideInvitedActive=document.getElementById("hideInvitedActive").value.substring(0,10);
//	   $('#auctionHideInvitedActive').html(hideInvitedActive);


//	   $('.i-checks').iCheck({
//		   checkboxClass: 'icheckbox_square-green',
//		   radioClass: 'iradio_square-green',
//	   });


    function setRowPrice(tableId, rowId, colNum, newValue) {
        $('#' + table).find('tr#' + rowId).find('td:eq(colNum)').html(newValue);
    }
});


$(".confirm").click(function (event) {

    var recordKey = this.id;
    var bidValue = document.getElementById(recordKey + "-bidValue").value;
    var auctionTypeKey = document.getElementById(recordKey + "-auctionTypeKey").value;
    var invitationKey = document.getElementById(recordKey + "-invitationKey").value;
    var companyUser = document.getElementById(recordKey + "-companyUser").value;

    analytics.identify('Bidding', {
        user: companyUser,
        bidValue: bidValue,
        auction: recordKey,
        serverTime: document.getElementById(recordKey+'-countdown-helper').dataset.servertime
    });

    var url = 'bidSell';
    if (auctionTypeKey == 'key.auctionType.buy') {
        url = 'bidBuy';

        stompClient.send("/app/" + url, {}, JSON.stringify({
            'recordKey': recordKey,
            'invitationKey': invitationKey,
            'bidValue': bidValue,
            'companyUser': companyUser
        }));
    }
    else{
        $.ajax({
            url: "/auction/auction/ajax/checkSellBidValue",
            type: "get",
            data: {auctionId: recordKey, bidValue: bidValue, companyUser: companyUser},
            // datatype: "json",

            success: function (response) {

                if (response) {

                    swal({
                        title: "შეტანილი ბიდი ძალიან დიდია",
                        text: "ნამდვილად გსურთ მითითებული ბიდის შეთავაზება?",
                        icon: "info",
                        buttons: [
                            'არა, უკან დამაბრუნე!',
                            'დიახ, გააგრძელე!'
                        ],
                        dangerMode: false,

                    }).then(function(isConfirm) {

                        if (isConfirm) {

                            stompClient.send("/app/" + url, {}, JSON.stringify({
                                'recordKey': recordKey,
                                'invitationKey': invitationKey,
                                'bidValue': bidValue,
                                'companyUser': companyUser
                            }));
                        }
                    })
                }
                else {
                    stompClient.send("/app/" + url, {}, JSON.stringify({
                        'recordKey': recordKey,
                        'invitationKey': invitationKey,
                        'bidValue': bidValue,
                        'companyUser': companyUser
                    }));
                }

            },

            error: function () {
                analytics.track('Ajax Request Failed', {
                    title: '500 Response code',
                });

                stompClient.send("/app/" + url, {}, JSON.stringify({
                    'recordKey': recordKey,
                    'invitationKey': invitationKey,
                    'bidValue': bidValue,
                    'companyUser': companyUser
                }));
            }
        });
    }


    // stompClient.send("/app/" + url, {}, JSON.stringify({
    //     'recordKey': recordKey,
    //     'invitationKey': invitationKey,
    //     'bidValue': bidValue,
    //     'companyUser': companyUser
    // }));
});


$(".refuse").click(function (event) {

    var recordKey = this.id;
    document.getElementById(recordKey + "-bidValue").value = null;

});


window.onkeyup = function (e, el) {


    var key = e.keyCode ? e.keyCode : e.which;
    // console.log("----------------------------------------" + key + "---------------------------------------------------");
    if (key == 13) {
        e.preventDefault();
        var classname = document.getElementsByClassName("confirm");


        for (var i = 0; i < classname.length; i++) {
            var bidValue = document.getElementById(classname[i].id + "-bidValue").value;


            if (bidValue > 0) {
                var btn = document.getElementById(classname[i].id);

                // console.log("----------------------------------------btn---------------------------------------------------");
                // console.log(btn);

                btn.click();
            }
        }
    }
};
  
