addEventListener("DOMContentLoaded", (event) => {
    const access = document.getElementById("access");
    const diet = document.getElementById("diet");
    const lang1 = document.getElementById("language1");
    const lang2 = document.getElementById("language2");
    const lang3 = document.getElementById("language3");
    const submitButton = document.getElementById("submitButton");
    let accessBool, dietBool, lang1Bool, lang2Bool, lang3Bool;
    access.addEventListener("input", () => {
        if(access.value !== ''){
            accessBool = true;
        }
        else{
            accessBool = false;
        }
        formVerify();
    });
    diet.addEventListener("input", () => {
        if(diet.value !== ''){
            dietBool = true;
        }
        else{
            dietBool = false;
        }
        formVerify();
    });
    lang1.addEventListener("input", () => {
        if(lang1.value !== ''){
            lang1Bool = true;
        }
        else{
            lang1Bool = false;
        }
        formVerify();
    });
    lang2.addEventListener("input", () => {
        if(lang2.value !== ''){
            lang2Bool = true;
        }
        else{
            lang2Bool = false;
        }
        formVerify();
    });
    lang3.addEventListener("input", () => {
        if(lang3.value !== ''){
            lang3Bool = true;
        }
        else{
            lang3Bool = false;
        }
        formVerify();
    });
    function formVerify() {
        if (accessBool == true && dietBool == true && lang1Bool == true 
            && lang2Bool == true && lang3Bool == true) {
            submitButton.removeAttribute("disabled");
        } else {
            submitButton.disabled = true;
        }
    }
});