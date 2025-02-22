tinymce.init({
    selector: '#textoNota',
    language: 'pt_BR',
    plugins: 'anchor autolink charmap codesample emoticons code image link lists media searchreplace table visualblocks wordcount linkchecker',
    toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table | align lineheight | numlist bullist indent outdent | emoticons charmap | removeformat',
    branding: false,
    entity_encoding: "raw"
});   

const tituloNota = document.getElementById("tituloNota");
const textoNota = document.getElementById("textoNota");
const corNota = document.getElementById("corNota");
const tipo = document.getElementById("tipo");
const idEmpresa = Number(document.getElementById("idEmpresa").value);
const timeElapsed = Date.now();
const hoje = new Date(timeElapsed);


// Cadastrar nota no mural
function cadastrarNota(){	
	const dados = { 
		titulo: tituloNota.value,
		texto: tinymce.get('textoNota').getContent(),
		data: hoje.toLocaleDateString(),
		usuario: 'Operação CPQD',
		cor: corNota.value+' text-white card text-white shadow',
		tipo: tipo.value,
		fkIdEmpresa: idEmpresa
	};
	
	if(dados.texto.trim() === ''){
		alert("Digite o texto da nota para salvar!");
		return false;
	  } else {
			fetch("/cadastrarmural", {
			  method: "POST",
			  headers: {
			    "Content-Type": "application/json",
			  },
			  body: JSON.stringify(dados),
			})
			  .then((response) => response.json());
			  
			  alert("Nota adicionada ao mural com sucesso!");	  
			  setTimeout(function() {
		    	window.location.reload();
			  }, 1000);
	}
}

// Apagar nota do mural
function apagarNota(id){
	if(confirm("Deseja realmente apagar esta nota?")){
		fetch('/apagarmural/'+id,{
	    	method: 'DELETE'
	  	});
	  		
	  setTimeout(function() {
    	window.location.reload();
	  }, 1000);
  	}
}