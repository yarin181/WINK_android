import React, {useState} from "react";
import "./InputBar.css"

function InputBar({ id ,string,Hide=false,onChange,validPassword=false,setAlert, submitAlert =false}) {

    const [showTooltip, setShowTooltip] = useState(false);
    //variable that contain the className
    const [errorStyle,setErrorStyle] =useState('input-register-style form-control');

    //the function represent the tooltip when the password input is focus
    function handleFocus() {
        if (id === "password") {
            setShowTooltip(true);
        }
    }



    //the function delete the tooltip when the password input is blur
    function handleBlur() {
        setShowTooltip(false);
        setErrorStyle(validPassword? " input-register-style form-control is-valid" : "form-control is-invalid input-register-style");
        validPassword ? setAlert(false) :  setAlert(true)
    }

    // handleBlurRef.current=handleBlur;

    function handleChange(event) {
        onChange(event.target.value);
    }
    if(submitAlert && !validPassword){
        setAlert(true)
    }


    return (
        <tr className="row-style">
            <td className="column-style" align="right">
                <label htmlFor={id}>{string}:</label>
            </td>
            <td className="column-style" align="left">{Hide ? (
                <input className={ (submitAlert && !validPassword)? 'form-control is-invalid input-register-style' : errorStyle}
                       type="password" id={id}
                       name="password"
                       onChange={handleChange} onFocus={handleFocus} onBlur={handleBlur}/>
            ) : (
                <input className={ (submitAlert && !validPassword)? 'form-control is-invalid input-register-style' : errorStyle}
                       type="text" id={id}
                       name="displayName"
                       onChange={handleChange} onBlur={handleBlur} />
            )}
            </td>
            <td>
                {showTooltip && id === "password" && (
                    //the tooltip
                    <div id="tooltip" className="fadeInLeft">
                        The password must contain:<br/>
                        1. At least one capital letter.<br/>
                        2. At least one digit.<br/>
                        3. Length of 8-16 characters.
                    </div>
                )}
            </td>

        </tr>

    );
}

export default InputBar;