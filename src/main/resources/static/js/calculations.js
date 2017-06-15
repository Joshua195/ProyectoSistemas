/* OPERACIONES PARA FINALIZAR EL CARRITO DE COMPRA */

function calculos(){
	var subtotal =  parseFloat(document.getElementById("subtotal").innerHTML);
	//alert(subtotal);

	var iva;
	var gastosEnvio = 0.0;
	/* Calculamos el iva y lo sumamos al subtotal*/
	var pais = document.getElementById("country").value;
	if(pais === "México"){
		//alert(pais);
		iva = subtotal * 0.16;
		document.getElementById("iva-porciento").innerHTML = "(16%):";
		document.getElementById("envio").innerHTML = 0.0;
		gastosEnvio = 0.0;
	}else if(pais === "Argentina"){
		iva = subtotal * 0.21;
		document.getElementById("iva-porciento").innerHTML = "(21%):";
		document.getElementById("envio").innerHTML = 500.00;
		gastosEnvio = 500.00;
	}else if(pais === "Chile"){
		iva = subtotal * 0.19;
		document.getElementById("iva-porciento").innerHTML = "(19%):";
		document.getElementById("envio").innerHTML = 500.00;
		gastosEnvio = 500.00;
	}else if(pais === "Guatemala"){
		iva = subtotal * 0.12;
		document.getElementById("iva-porciento").innerHTML = "(12%):";
		document.getElementById("envio").innerHTML = 500.00;
		gastosEnvio = 500.00;
	}else if(pais === "Venezuela"){
		iva = subtotal * 0.12;
		document.getElementById("iva-porciento").innerHTML = "(12%):";
		document.getElementById("envio").innerHTML = 500.00;
		gastosEnvio = 500.00;
	}

	iva = iva.toFixed(2);
	document.getElementById("iva").innerHTML = iva;

	/*	Calculamos nuevo subtotal */
	subtotal += parseFloat(iva);
	//alert(subtotal);
	/* Verificamos si hay cupon de descuento */

	var cupon = document.getElementById("cupon").value;
	var descuento;
	//alert(cupon);
	if(cupon === "feliznavidad"){
		var d = new Date();
		if(d.getMonth()==11){
			alert('Hay descuento por el mes de diciembre %25');
			descuento = parseFloat(subtotal*0.25);
		}
	}else if(cupon === "vacaciones"){
		alert('Hay descuento %10');
		descuento = parseFloat(subtotal*0.10);
	}else if(cupon === "cumple"){
		alert('Hay descuento %20')
		descuento = parseFloat(subtotal*0.20);
	}else{
		descuento = parseFloat(0);
	}
	descuento = descuento.toFixed(2);

	/* Colocamos descuento */
	document.getElementById("descuento").innerHTML = descuento;

	/* Sacamos el total */
	var total;
	total = (subtotal - descuento) + gastosEnvio;
	total = total.toFixed(2);	//Redondeo a 2 digitos
	document.getElementById("total").innerHTML = total;


	document.getElementById("hiddenIva").value = iva;
	document.getElementById("cuponFinal").value = document.getElementById("cupon").value;
	document.getElementById("gastosenvio").value = gastosEnvio;
	document.getElementById("totalFinal").value = total

}


/* Función para los radio-button de la Forma de pago */

function radios(radioNum){
	if(radioNum==1){
		//alert(radioNum);
		document.getElementById("radio-pago1").checked = true;
		document.getElementById("radio-pago2").checked = false;
	}else if(radioNum==2){
		document.getElementById("radio-pago2").checked = true;
		document.getElementById("radio-pago1").checked = false;
	}
}