/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function getLessonByTypes(id) {
    var e = document.getElementById("lessonCategories");
    var id = e.options[e.selectedIndex].value;
    var url = "http://localhost/hobbyhorse/index.php/lesson/getAjaxLessonsByLessonType/"+id;
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
    if(elementToUpdate!=undefined) {
        while(elementToUpdate.firstChild) {
            elementToUpdate.removeChild(elementToUpdate.firstChild);
        }
    }
    alert(resp);
    var lessonJson = eval('(' + resp + ')');
    for(j=0;j<lessonJson.lessons.length;j++) {
        var divElement = document.createElement("DIV");
        divElement.setAttribute("class","grid_5");
        var h4Element = document.createElement("h4");
        var h4Text = document.createTextNode(lessonJson.lessons[j].name)
        var pElement = document.createElement("p");
        var pText = document.createTextNode(lessonJson.lessons[j].description)
        h4Element.appendChild(h4Text);
        pElement.appendChild(pText);
    }
}

