function addTable() 
{
    var measures = getMeasuresFromREST();
    var table = createHtmlTable(measures);

    var myDynTable = document.getElementById("myDynTableDiv");
    myDynTable.innerHTML = "";
    myDynTable.appendChild(table);
}

function getMeasuresFromREST() 
{
    var measures = new Array();

    var xhr = new XMLHttpRequest();    
    xhr.open("get", "http://localhost:4444/rest/measures", false);
    xhr.setRequestHeader("Accept", "application/xml");
    xhr.onreadystatechange = function () 
    {
        if (xhr.readyState == 4 && xhr.status == 200) {

          extractMeasuresFromXml(xhr, measures);
        }
    };
    xhr.send();

    return measures;
}

function extractMeasuresFromXml(xhr, measures) 
{
    var xmlDoc = xhr.responseXML;
    var allmeasureNodes = xmlDoc.getElementsByTagName("measure");
    for (i = 0; i < allmeasureNodes.length; i++) 
    {
        var measureNode = allmeasureNodes[i];

        var temperature = getChildNodeValue(measureNode, "temperature");
        var humidity = getChildNodeValue(measureNode, "humidity");
        var windspeed = getChildNodeValue(measureNode, "windspeed");
        var day = getChildNodeValue(measureNode, "day");
        var id = getChildNodeValue(measureNode, "id");
        
        measures.push([temperature, humidity, windspeed, day, id]);
    }  
}

function getChildNodeValue(element, name) 
{
    return getChildElement(element, name).childNodes[0].nodeValue;
}

function getChildElement(element, name) 
{
    var child = element.firstChild;
    while (child) 
    {
        if (child.nodeType == Node.ELEMENT_NODE && child.nodeName == name) 
        {
            return child;
        }
        child = child.nextSibling;
    }
}

function createHtmlTable(measures) 
{
    var table = document.createElement("TABLE");
    table.id = "myTable"
    table.border = "1";

    // Add the header row.
    var headerColumns = ["", "temperature", "humidity", "windspeed", "Date", "id"];
    var headerColumnsCount = headerColumns.length;
    var row = table.insertRow(-1);
    for (var i = 0; i < headerColumnsCount; i++) 
    {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = headerColumns[i];
        row.appendChild(headerCell);
    }

    // Add the data rows.
    var columnCount = measures[0].length;
    for (var i = 0; i < measures.length; i++) 
    {
        row = table.insertRow(-1);

        var cell = row.insertCell(-1);
        var id = measures[i][4];
        cell.innerHTML = "<input type='button' value = 'Delete' " + 
                         "onClick='Javacsript:deleteRow(this,"+id+")'>";

        for (var j = 0; j < columnCount; j++) 
        {
            var cell = row.insertCell(-1);
            cell.innerHTML = measures[i][j];
        }
    }

    return table;
}

function deleteRow(obj, id) 
{
    var index = obj.parentNode.parentNode.rowIndex;
    var table = document.getElementById("myTable");
    table.deleteRow(index);

    deleteMeasureWithREST(id);
    addTable();
}

function deleteMeasureWithREST(id) {

    var xhr = new XMLHttpRequest();    
    xhr.open("delete", "http://localhost:4444/rest/measures/" + id, false);
    xhr.send();
}

function createMeasureWithREST() 
{
    var form = document.getElementById("createForm");
    var queryString = joinFormParameters(form);

    var xhr = new XMLHttpRequest();    
    xhr.open("post", "http://localhost:4444/rest/measures/", false);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); 
    xhr.setRequestHeader("Content-length", queryString.length);
    xhr.send(queryString);

    addTable();
}

function joinFormParameters(form) 
{    
    var keyvaluepairs = [];

    for (var i = 0; i < form.elements.length; i++ ) 
    {
        var e = form.elements[i];
        keyvaluepairs.push(encodeURIComponent(e.name) + "=" + encodeURIComponent(e.value));
    }
    
    return keyvaluepairs.join("&");
}