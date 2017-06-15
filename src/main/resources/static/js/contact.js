$(document).ready(function() {
    $("#contact_form").bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {            
             name: {
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
            phone: {
                validators: {
                    notEmpty: {
                        message: 'Por favor completa tú número telefónico'
                    }
                }
            },
            address: {
                validators: {
                     stringLength: {
                        min: 8,
                    },
                    notEmpty: {
                        message: 'Por favor completa tú dirección'
                    }
                }
            },
            state: {
                validators: {
                    notEmpty: {
                        message: 'Por favor selecciona un estado.'
                    }
                }
            },
            comment: {
                validators: {
                      stringLength: {
                        min: 10,
                        max: 200,
                        message:'Por favor envíanos tú mensaje con mínimo 10 caracteres y máximo 200'
                    },
                    notEmpty: {
                        message: 'Por favor completa tú mensaje'
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

