	function boxToField(box, fieldId) {
		var field = document.getElementById(fieldId);
		if (box.checked) {
			field.value = '1';
		} else {
			field.value = '0';
		}
	}

	function activate(id) {
		var form = document.getElementById("actionForm");
		form.idForRestore.value=id;
		form.mode.value="RESTORE";
		form.submit();
	}
	function deactivate(id) {
		var form = document.getElementById("actionForm");
		form.idForDeletion.value=id;
		form.mode.value="DELETE";
		form.submit();
	}
	
	function hideEdit(){
		var popup = document.getElementById("editForm");
		popup.style.display = "none";
	}
	
	function hidePaginator(){
		var popup = document.getElementById("paginatorForm");
		popup.style.display = "none";
	}
	
	function showPaginator(){
		hideEdit();
		var popup = document.getElementById("paginatorForm");
		popup.style.display = "block";
	}
	
	function togglePaginator() {
		var popup = document.getElementById("paginatorForm");
		if (popup.style.display != 'block') {
			showPaginator();
		} else {
			hidePaginator();
		}
	}