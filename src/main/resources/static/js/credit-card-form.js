$(document).ready(function() {
    $('#creditCardForm')
        .bootstrapValidator({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                cardType: {
                    validators: {
                        notEmpty: {
                            message: 'Tipo de tarjeta requerido'
                        }
                    }
                },
                cc: {
                    validators: {
                        notEmpty: {
                            message: 'Numero de tarjeta requerido'
                        },
                        creditCard: {
                            message: 'Numero no valido'
                        }
                    }
                },
                mes:{
                    validators:{
                        notEmpty:{
                            message: 'Seleccione un mes'
                        }
                    }
                },
                año:{
                    validators:{
                        notEmpty:{
                            message: 'Seleccione un año'
                        }
                    }
                },
                titular:{
                    validators:{
                        stringLength: {
                            min: 10,
                            message: 'Mínimo 10 caracteres'
                        },
                        notEmpty:{
                            message: 'Complete el nombre del titular'
                        }
                    }
                },
                codigo:{
                    validators:{
                        cvv:{
                            creditCardField: 'cc',
                            message: 'Codigo no valido'
                        },
                        notEmpty:{
                            message: 'Introduzca codigo'
                        }
                    }
                }
            },
            /* Si el llenado es exitoso */
            submitHandler: function(validator, form, submitButton) {
                document.getElementById("validacion0").value = "valido";
                document.getElementById("validacion0").innerHTML = "Valido";
                document.getElementById("validacion0").style.background='#00b33c';

                /* Cerramos form */
                var form = document.getElementById("creditCardForm");
                var elements = form.elements;
                for (var i = 0, len = elements.length; i < len; ++i) {
                    elements[i].readOnly = true;
                }
            }
        })
        .on('success.validator.fv', function(e, data) {
            // data.field     ---> The field name
            // data.validator ---> The validator name
            // data.fv        ---> The plugin instance

            // Whenever user changes the card type,
            // we need to revalidate the credit card number
            if (data.field === 'cardType') {
                data.fv.revalidateField('cc');
            }

            if (data.field === 'cc' && data.validator === 'creditCard') {
                // data.result.type can be one of
                // AMERICAN_EXPRESS, DINERS_CLUB, DINERS_CLUB_US, DISCOVER, JCB, LASER,
                // MAESTRO, MASTERCARD, SOLO, UNIONPAY, VISA

                var currentType = null;
                switch (data.result.type) {
                    case 'AMERICAN_EXPRESS':
                        currentType = 'Ae';         // Ae is the value of American Express card in the select box
                        break;

                    case 'MASTERCARD':
                    case 'DINERS_CLUB_US':
                        currentType = 'Master';     // Master is the value of Master card in the select box
                        break;

                    case 'VISA':
                        currentType = 'Visa';       // Visa is the value of Visa card in the select box
                        break;

                    default:
                        break;
                }

                // Get the selected type
                var selectedType = data.fv.getFieldElements('cardType').val();
                alert(selectedType);
                if (selectedType && currentType !== selectedType) {
                    // The credit card type doesn't match with the selected one
                    // Mark the field as not valid
                    data.fv.updateStatus('cc', data.fv.STATUS_INVALID, 'creditCard');
                }
            }
        });
});

