// Cadastrar script
function cadastrarScript(){	
	const dados = { 
		titulo: tituloScript.value,
		categoria: categoriaScript.value,
		caminho: caminhoScript.value,
		fkIdEmpresa: Number(document.getElementById("idEmpresa").value)
	};	
	fetch("/cadastrarscript", {
	  method: "POST",
	  headers: {
	    "Content-Type": "application/json",
	  },
	  body: JSON.stringify(dados),
	})
	  .then((response) => response.json());
	  
	  alert("Script cadastrado com sucesso!");
	  setTimeout(function() {
    	window.location.reload();
	  }, 1000);
}

// Apagar script
function apagarScript(id){
	if(confirm("Deseja realmente apagar este script?")){
		fetch('/apagarscript/'+id,{
	    	method: 'DELETE'
	  	});	
	  	
	  setTimeout(function() {
    	window.location.reload();
	  }, 1000);
  	}
}