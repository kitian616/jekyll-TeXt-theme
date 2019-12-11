function encryptElement(pubKey, element) {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.openpgp, async function () {
    let options = {
      message: openpgp.message.fromText(element.value),
      publicKeys: (await openpgp.key.readArmored(pubKey)).keys
    };
    openpgp.encrypt(options).then(ciphertext => {
      element.value = ciphertext.data;
      element.setAttribute("encrypt", "false");      
    });
  });
};

function submitForm() {
  let validForm = $('#contact-form:valid');
  if (validForm) {
    let submit = validForm.get(0).submit;
    let toEncrypt = validForm.find("[encrypt=true]");
    if (window.TEXT_VARIABLES.pubKey && toEncrypt.length) {
      if (submit) submit.value = "Encrypting...";
      toEncrypt.each(function () {
        encryptElement(atob(window.TEXT_VARIABLES.pubKey), this);
      });
      setTimeout(submitForm, 1000);
    }
    else {
      if (submit) submit.value = "Submitting...";
      HTMLFormElement.prototype.submit.call(validForm.get(0));
    }
  }
  return false;

}