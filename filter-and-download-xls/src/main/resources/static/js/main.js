'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

var multipleUploadForm = document.querySelector('#multipleUploadForm');
var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
var multipleFileUploadError = document.querySelector('#multipleFileUploadError');
var multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');

function uploadSingleFile(file) {
	var category = document.getElementById("category");
	var selectedCategory = category[category.selectedIndex].value
    var formData = new FormData();
    formData.append("file", file);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/upload/" +selectedCategory);

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            
            var columns = "";
            response.columns.forEach(column => columns = columns + "<option value=\""+column+"\">" +getColumnValue(column)+ "</option>")
            
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>Filename : " +response.filename+ "<br> Number of rows : " +response.rows+ "</p>"
            + "<form id=\"filterForm\" name=\"filterForm\" action=\"/filter\" method=\"get\">"
            + "Filter: <select name=\"column\" id=\"column\" class=\"general\">" 
            + columns
            + "</select>"
            + "<input id=\"filterValue\" type=\"text\" name=\"filterValue\" class=\"general\" placeholder=\"value\" required />"
			+ "<button type=\"submit\" class=\"primary submit-btn\">Submit</button>"
			+ "</form>"
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);

filterForm.addEventListener('submit', function(event){
    downloadFilteredXls();
    event.preventDefault();
}, true);

function downloadFilteredXls() {
	var column = document.getElementById("column");
	var selectedColumn = column[column.selectedIndex].value
	var filterValue = document.getElementById("filterValue");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/filter/" +selectedColumn+ "/" +filterValue);

    /*xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            
            var columns = "";
            response.columns.forEach(column => columns = columns + "<option value=\"category_id\">" +column+ "</option>")
            
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>Filename : " +response.filename+ "<br> Number of rows : " +response.rows+ "</p>"
            + "<form id=\"filterForm\" name=\"filterForm\" action="/filter" method="get">"
            + "Filter: <select name=\"category\" id=\"category\" class=\"general\">" 
            + "<option value=\"category_id\">None</option>"
            + columns
            + "</select>"
            + "<input id=\"filterValue\" type=\"text\" name=\"filterValue\" class=\"general\" placeholder=\"value\" required />"
			+ "<button type=\"submit\" class=\"primary submit-btn\">Submit</button>"
			+ "</form>"
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }*/
}

function getColumnValue(column) {
	column = column.replace( /([A-Z])/g, " $1" );
	return column.charAt(0).toUpperCase() + column.slice(1);
}
