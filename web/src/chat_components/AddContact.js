import React, {useState} from "react";

function AddContact({onAdd,contacts,handleError}){
    const [name, setName] = useState('');

    const addContent = () =>{
        // const isNameInContact = contacts.some(item => item.user.username === name)
        // if (isNameInContact) {
        //     handleError(name + " is Already added!");
        //     setName('');
        //     return;
        // }
        if (name === ''){
            handleError('Insert A Valid Name');
            return;
        }
        const contact = {username: name}
        onAdd(contact);
        setName('');
    }
    return(
        <div className="modal fade" id="exampleModal" tabIndex={-1} aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">Add new content</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"/>
                    </div>
                    <div className="modal-body">
                        <div className="mb-3">
                            <input type="text" className="form-control" id="inputName" placeholder="contents identifier" value={name} onChange={e => setName(e.target.value)}/>
                        </div>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn add_friend" data-bs-dismiss="modal" onClick={addContent}>Add</button>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default AddContact;