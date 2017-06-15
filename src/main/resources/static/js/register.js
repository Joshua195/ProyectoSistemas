$(document).ready(function() {
    $("#contact_form").bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {            
             last_name: {
                validators: {
                     stringLength: {
                        min: 2,
                    },
                    notEmpty: {
                        message: 'Por favor completa tú nombre'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'Por favor completa tú correo electrónico'
                    },
                    emailAddress: {
                        message: 'Por favor introduce un correo electrónico válido'
                    }
                }
            },
            user: {
                validators: {
                    notEmpty: {
                        message: 'Por favor completa tú Nombre de usuario'
                    }
                }
            },
            password: {
                validators: {
                     stringLength: {
                        min: 8,
                        max: 15,
                        message:'Minimo 8 caracteres y máximo 15'
                    },
                    notEmpty: {
                        message: 'Por favor completa contraseña'
                    },
                    identical:{
                        field: 'password2',
                        message: 'La contraseña y la confirmación no son iguales'
                    }
                }
            },
            password2: {
                validators: {
                     stringLength: {
                        min: 8,
                        max: 15,
                        message:'Minimo 8 caracteres y máximo 15'
                    },
                    notEmpty: {
                        message: 'Por favor selecciona un estado.'
                    },
                    identical:{
                        field: 'password',
                        message: 'La contraseña y la confirmación no son iguales'
                    }
                }
            },
            question: {
                validators: {
                    notEmpty: {
                        message: 'Por favor selecciona un pregunta.'
                    }
                }
            },
            answer: {
                validators: {
                    notEmpty: {
                        message: 'Por favor responde a la pregunta.'
                    }
                }
            }
            }
        })
        .on('success.form.bv', function(e) {
            $('#success_message').slideDown({ opacity: "show" }, "slow") // Do something ...
                $('#contact_form').data('bootstrapValidator').resetForm();

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