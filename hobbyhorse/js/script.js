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
        divElement.setAttribute("class","grid_5");
        var h4Element = document.createElement("h4");
        var h4Text = document.createTextNode(lessonJson[j].name)
        var pElement = document.createElement("p");
        var pElementDate = document.createElement("p");
        var pText = document.createTextNode(lessonJson[j].description)
        var pTextDate = document.createTextNode("Starts on: "+lessonJson[j].createDate)
        h4Element.appendChild(h4Text);
        pElement.appendChild(pText);
        pElementDate.appendChild(pTextDate);
        divElement.appendChild(h4Element);
        divElement.appendChild(pElementDate);
        divElement.appendChild(pElement);
        elementToUpdate.appendChild(divElement);
    }
}

