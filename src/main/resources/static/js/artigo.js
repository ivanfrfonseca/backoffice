tinymce.init({
    selector: '#textoArtigo',
    language: 'pt_BR',
    plugins: 'anchor autolink charmap codesample emoticons code image link lists media searchreplace table visualblocks wordcount linkchecker',
    toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table | align lineheight | numlist bullist indent outdent | emoticons charmap | removeformat',
    branding: false,
    entity_encoding: "raw"
});    

tinymce.init({
    selector: '#editarTextoArtigo',
    language: 'pt_BR',
    plugins: 'anchor autolink charmap codesample emoticons code image link lists media searchreplace table visualblocks wordcount linkchecker',
    toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table | align lineheight | numlist bullist indent outdent | emoticons charmap | removeformat',
    branding: false,
    entity_encoding: "raw"
});     
    
const timeElapsed = Date.now();
const hoje = new Date(timeElapsed);
const idEmpresa = Number(document.getElementById("idEmpresa").value);

// Cadastrar artigo
function cadastrarArtigo(){	
	const dados = {
		titulo: tituloArtigo.value,
		texto: tinymce.get('textoArtigo').getContent(),
		data: hoje.toLocaleDateString(),
		fkIdEmpresa: idEmpresa
	};
	
	if(dados.texto.trim() === ''){
		alert("Digite o texto do artigo para salvar!");
		return false;
	  } else {
		  fetch("/cadastrarartigo", {
			method: "POST",
			headers: {
			  "Content-Type": "application/json",
			},
			body: JSON.stringify(dados),
		  })
		    .then((response) => response.json());
			  
			alert("Artigo cadastrado com sucesso!");
			
			if(idEmpresa === 1){
				window.location.href = "/sto/baseconhecimento";	
			} else if(idEmpresa === 2){
				window.location.href = "/conectcar/baseconhecimento";
			} else if(idEmpresa === 3){
				window.location.href = "/stp/baseconhecimento";
			} 			 
	}
}

// Editar artigo
function editarArtigo(id){	
	const dados = {
		id: id, 
		titulo: editarTituloArtigo.value,
		texto: tinymce.get('editarTextoArtigo').getContent(),
		fkIdEmpresa: idEmpresa,
		data: hoje.toLocaleDateString()
	};
	
	if(dados.texto.trim() === ''){
		alert("Digite o texto do artigo para salvar!");
		return false;
	} else {
		  fetch("/editarartigo", {
		    method: "PUT",
		    headers: {
		      "Content-Type": "application/json",
		    },
		    body: JSON.stringify(dados),
		  })
		    .then((response) => response.json());
	      
	        alert("Artigo editado com sucesso!");
		    
			if(idEmpresa === 1){
				window.location.href = "/sto/baseconhecimento";	
			} else if(idEmpresa === 2){
				window.location.href = "/conectcar/baseconhecimento";
			} else if(idEmpresa === 3){
				window.location.href = "/stp/baseconhecimento";
			}
	}
}

// Apagar artigo
function apagarArtigo(id){
	if(confirm("Deseja realmente apagar este artigo?")){
		fetch('/apagarartigo/'+id,{
	    	method: 'DELETE'
	  	});
	  	
	    setTimeout(function() {
    		window.location.reload();
	  	}, 1000);
  	}
}
