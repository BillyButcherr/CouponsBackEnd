import { SyntheticEvent, useState } from "react";
import "./Modal.css";

function Modal(props: any): JSX.Element {
    const modal = props.firstHook;
    const setModal = props.secondHook;

    function toggleModal() {
        setModal(!modal);
    }
    function stop(e: SyntheticEvent) {
        // e.stopPropagation();
    }
    return (
        <>
            {modal && (
                <div className="Modal">
                    <div className="overlay" onClick={toggleModal}/>
                    <div className="modal-content">
                        {props.children}
                    </div>
                </div>
            )}
        </>
    );
}

export default Modal;
