/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

function getLessonByTypes(id) {
    var e = document.getElementById("lessonCategories");
    var id = e.options[e.selectedIndex].value;
    var url = "http://mysite.fb/hobbyhorse/index.php/lesson/getAjaxLessonsByLessonType/"+id;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            updateResponse(xmlhttp.responseText);
        }
    }
    xmlhttp.open("GET",url,true);
    xmlhttp.send();
}

function updateResponse(resp) {
    var elementToUpdate = document.getElementById("lessonList");        
    var host = "http://mysite.fb/hobbyhorse/";
    if(elementToUpdate!=undefined) {
        while(elementToUpdate.firstChild) {
            elementToUpdate.removeChild(elementToUpdate.firstChild);
        }
    }
    var lessonJson = eval('(' + resp + ')');
    if(lessonJson.length==0) {
        var divElement = document.createElement("DIV");
        divElement.setAttribute("class","grid_5");
        var pElement = document.createElement("p");
        var txtNoLesson = document.createTextNode("There are no active lessons for this category.");
        pElement.appendChild(txtNoLesson);        
        divElement.appendChild(pElement);
        elementToUpdate.appendChild(divElement); 
    }
    for(j=0;j<lessonJson.length;j++) {
        var divElement = document.createElement("DIV");
        divElement.setAttribute("class","div2");
        var h4Element = document.createElement("h4");
        var h5Element = document.createElement("h5");        
        h5Element.setAttribute("class","expertname");
        h4Element.setAttribute("class","lessonName");
        h4Element.setAttribute("id","lessonName"+lessonJson[j].id)
        var h4Text = document.createTextNode(lessonJson[j].name.capitalize())
        var h5Text = document.createTextNode("The Expert: " +lessonJson[j].username);
        var divRatingElement = document.createElement("div");
        divRatingElement.setAttribute("class","rating");
        var divRatingText = document.createTextNode("Ratings for the Expert: ");
        var brRating = document.createElement("br");
        if(lessonJson[j].rating>0){
            divRatingElement.appendChild(divRatingText);
            divRatingElement.appendChild(brRating);
        }
        for(k=0;k<lessonJson[j].rating;k++) {
            var divImageElement = document.createElement("img");
            divImageElement.src=host+"images/gold_star.jpeg";
            divRatingElement.appendChild(divImageElement);
        }
        var pElementDate = document.createElement("p");
        var pText = document.createTextNode(lessonJson[j].description)
        var formattedDate = formatDate(lessonJson[j].eventDate);
        var brElement = document.createElement("br");
        var pTextDate = document.createTextNode("Starts on: "+formattedDate+" at "+lessonJson[j].eventTime)
        var joinBtn = document.createElement("input");
        joinBtn.setAttribute("type", "button");
        joinBtn.setAttribute("id", lessonJson[j].id);
        joinBtn.setAttribute("name", "joinLesson");
        joinBtn.setAttribute("value", "Join");
        joinBtn.setAttribute("onClick", "joinLesson("+lessonJson[j].id+");disableElement(this);");
        h4Element.appendChild(h4Text);
        h5Element.appendChild(h5Text);
        pElementDate.appendChild(pTextDate);
        divElement.appendChild(h4Element);
        divElement.appendChild(h5Element);
        divElement.appendChild(pElementDate);
        divElement.appendChild(pText);
        divElement.appendChild(divRatingElement);
        divElement.appendChild(joinBtn);
        elementToUpdate.appendChild(divElement);
        checkIfLessonJoined(lessonJson[j].id);
    }
}

function findMonth(month) {
    if(month==0) {
        return "Jan";
    }
    if(month==1) {
        return "Feb";
    }
    if(month==2) {
        return "Mar";
    }
    if(month==3) {
        return "Apr";
    }
    if(month==4) {
        return "May";
    }
    if(month==5) {
        return "Jun";
    }
    if(month==6) {
        return "Jul";
    }
    if(month==7) {
        return "Aug";
    }
    if(month==8) {
        return "Sep";
    }
    if(month==9) {
        return "Oct";
    }
    if(month==10) {
        return "Nov";
    }
    if(month==11) {
        return "Dec";
    }
}

function formatDate(dateString) {
    var dateFormat = new Date(dateString);
    var date = dateFormat.getUTCDate();
    var month = findMonth(dateFormat.getUTCMonth());
    var year = dateFormat.getUTCFullYear();
    
    return month+" "+date+", "+year;
}

function joinLesson(id, name) {
    var url = "http://mysite.fb/hobbyhorse/index.php/lesson/joinLesson/"+id+"/"+name;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET",url,true);
    xmlhttp.send();
}

function checkResponse(resp) {
    var lessonJson = eval('(' + resp + ')');
    if(lessonJson.length > 0) {
        document.getElementById(lessonJson[0].lessonId).disabled = true;
        document.getElementById("lessonName"+lessonJson[0].lessonId).innerHTML = "<a href='http://mysite.fb/hobbyhorse/index.php/lesson/main/"+lessonJson[0].lessonId+"'>"+lessonJson[0].name.capitalize()+"</a>";
    }
}

function postComment(elementId) {
    var lessonName = encodeURIComponent(document.getElementById("lessonName").value);
    var description = encodeURIComponent(document.getElementById("comment").value);
    if(description=="") {
        alert("Please enter a comment!");
        return false;
    }
    var rating = encodeURIComponent(calculateRating());
    if(rating==0) {
        alert("Please rate the lesson!");
        return false;
    }
    var lessonId = encodeURIComponent(document.getElementById("lessonId").value);
    var url = "http://mysite.fb/hobbyhorse/index.php/comment/saveComment/"+lessonName+"/"+description+"/"+lessonId+"/"+rating;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            updateMainLessonPage(xmlhttp.responseText);
        }
    }
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send();
    disableElement(elementId);
    return true;
}


function updateMainLessonPage(resp) {
    var commentJson = eval('(' + resp + ')');
    if(commentJson.comments.length > 0) {
        var lastComment = commentJson.comments[0].description;
    }    
    var allComments = document.getElementById("commentsDiv");
    var liElement = document.createElement("li");
    liElement.appendChild(document.createTextNode(lastComment));
    allComments.appendChild(liElement);
    document.getElementById("comment").value="";
}

function checkIfLessonJoined(id) {
    var url = "http://mysite.fb/hobbyhorse/index.php/lesson/checkIfAlreadyJoined/"+id;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            checkResponse(xmlhttp.responseText);
        }
    }
    xmlhttp.open("GET",url,true);
    xmlhttp.send();
}

function disableElement(element) {
    element.disabled = true;
}

function changeLessonTypeImage() {
    var e = document.getElementById("lessonCategories");
    var id = e.options[e.selectedIndex].value;
    document.getElementById("lessontypeimg").src="/hobbyhorse/images/lessontype/"+id+".jpg";
}

function checkIfCommented(id) {
    var url = "http://mysite.fb/hobbyhorse/index.php/comment/checkCommented/"+id;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            checkResponseComment(xmlhttp.responseText);
        }
    }
    xmlhttp.open("GET",url,true);
    xmlhttp.send();
}

function checkResponseComment(resp) {
    var commentJson = eval('(' + resp + ')');
    if(commentJson.length > 0) {
        document.getElementById('post').disabled = true;
    }
}

function changeImage(element) {
    var host = "http://mysite.fb/hobbyhorse/";
    var ida = element.id.substring(3,4);
    if(element.src==host+"images/gold_star.jpeg") {
        for(i=parseInt(ida);i<5;i++) {
            var newid = i+1;
            var el = document.getElementById("img"+newid);
            el.src = host+"images/white_star.jpeg";
        }
    }
    if(element.src==host+"images/white_star.jpeg") {
        for(i=0;i<parseInt(ida);i++) {
            var newid = i+1;
            var el = document.getElementById("img"+newid);
            el.src = host+"images/gold_star.jpeg";
        }
    }
    
}

function calculateRating() {
    var host = "http://mysite.fb/hobbyhorse/";
    var rating = 0;
    for(i=1;i<6;i++) {
        if(document.getElementById("img"+i).src==host+"images/gold_star.jpeg") {
            rating = i;
        }
    }
    return rating;
}

function checkCommentCanBePosted(eventDateTime) {
    var today = new Date();
    var diffInMinutes = ((new Date(eventDateTime) - today)/(1000*60));
    if(diffInMinutes > 0) {
        document.getElementById("post").disabled = true;
        return;
    }  
}


function checkExpertHobbyistAndTimeOfLesson(lessonUserId, username, eventDateTime, isLiveVal) {
    var today = new Date();
    var diffInMinutes = ((new Date(eventDateTime) - today)/(1000*60));
    if(diffInMinutes > 5) {
        document.getElementById("startLesson").disabled = true;
        return;
    }
    var host = "http://mysite.fb/hobbyhorse/";
    var url = host+"index.php/user/getUserByUsername/"+username;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            checkId(lessonUserId, isLiveVal, xmlhttp.responseText);
        }
    }
    xmlhttp.open("GET",url,true);
    xmlhttp.send();
    
}

function checkId(lessonUserId,isLiveVal,resp){
    var userJson = eval('(' + resp + ')');
    if(userJson[0].id!=lessonUserId && isLiveVal==0){
        document.getElementById("startLesson").disabled=true;
        return;
    }
    if(userJson[0].id==lessonUserId){
        document.getElementById("startLesson").disabled=false;
        return;
    }
    if(userJson[0].id!=lessonUserId && isLiveVal==1){
        document.getElementById("startLesson").disabled=false;
        return;
    }
}

function updateLessonObject(lessonId,isLiveVal) {
    var lessonId = encodeURIComponent(lessonId);
    var isLiveVal = encodeURIComponent(isLiveVal);
    var url = "http://mysite.fb/hobbyhorse/index.php/lesson/updateLessonIsLive/"+lessonId+"/"+isLiveVal;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            if(isLiveVal==0) {
                reloadPage(xmlhttp.responseText);
            }

        }
    }
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send();  
}

function reloadPage(resp){
    window.location.href=window.location.href;
}


function updateWasAttended(lessonId) {
    var lessonId = encodeURIComponent(lessonId);
    var url = "http://mysite.fb/hobbyhorse/index.php/lesson/updateWasAttended/"+lessonId;
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            (xmlhttp.responseText);
        }
    }
    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send();  
}
