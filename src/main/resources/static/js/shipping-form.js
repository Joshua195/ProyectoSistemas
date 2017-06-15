$(document).ready(function() {
    $("#shipping-form").bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {            
             address: {
                validators: {
                     stringLength: {
                        min: 15,
                        message: 'Mínimo 15 caracteres'
                    },
                    notEmpty: {
                        message: 'Por favor completa tu dirección'
                    }
                }
            },
            city: {
                validators: {
                    notEmpty: {
                        message: 'Por favor completa tu ciudad'
                    }
                }
            },
            postal: {
                validators: {    
                    notEmpty: {
                        message: 'Por favor completa tu codigo postal'
                    },
                    zipCode: {
                        country: 'US',
                        message: 'Min. 4 digitos - Max. 5 digitos'
                    }
                }
            },
            country: {
                validators: {
                    notEmpty: {
                        message: 'Por favor selecciona una ciudad.'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'Por favor pon un telefono de contacto.'
                    },
                    phone: {
                        message: '10 digitos'
                    }
                }
            }
            }
        })
        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({ opacity: "show" }, "slow") // Do something ...
                $('#shipping-form').data('bootstrapValidator').resetForm();

            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function(result) {
                console.log(result);
            }, 'json');
        });
});

function validacionGral(){
    /* Verificamos si selecciono uno de los dos metodos de pago */
    if((document.getElementById("radio-pago1").checked == false) && document.getElementById("radio-pago2").checked == false){
        alert('Selecciona un método de pago')
        return false
    }

    /* Verificamos si el metodo de pago ya fué validado */
    if(document.getElementById("radio-pago1").checked == true){
        if(document.getElementById("validacion0").value != "valido"){
            alert('Pago con Tarjeta de credito no validado o campos incompletos.')
            return false
        }else{
            return true
        }
    }

    if(document.getElementById("radio-pago2").checked == true){
        if(document.getElementById("validacion").value != "valido"){
            alert('Pago con Paypal no validado o campos incompletos.')
            return false
        }else{
            return true
        }
    }
}