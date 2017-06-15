$(document).ready(function() {
    $('#paypalForm')
        .bootstrapValidator({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                emailPaypal:{
                    validators:{
                        notEmpty: {
                            message: 'Correo electrónico de Paypal'
                        },
                        emailAddress: {
                            message: 'Introduzca correo electrónico valido'
                        }
                    }
                },
                passwordPaypal:{
                    validators:{
                        notEmpty: {
                            message: 'Introduzca contraseña por favor'
                        }
                    }
                }
            },
            /* Si el llenado es exitoso */
            submitHandler: function(validator, form, submitButton) {
                document.getElementById("validacion").value = "valido";
                document.getElementById("validacion").innerHTML = "Valido";
                document.getElementById("validacion").style.background='#00b33c';

                /* Cerramos form */
                var form = document.getElementById("paypalForm");
                var elements = form.elements;
                for (var i = 0, len = elements.length; i < len; ++i) {
                    elements[i].readOnly = true;
                }
            }
        })
});

function noValido() {
    document.getElementById("validacion").value = "";
    document.getElementById("validacion").innerHTML = "Validar";
}
