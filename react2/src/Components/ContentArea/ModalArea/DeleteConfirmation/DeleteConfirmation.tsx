import Content from "../../Content/Content";
import Modal from "../Modal/Modal";
import "./DeleteConfirmation.css";
// interface DeleteInfo {
//     text: string;
//     accept: string;
//     decline: string;
//     itemId: string;
//     tableHooks: any;
// }
// interface deleteProps{
//     actionHooks:ModalState;
// }
function DeleteConfirmation(props: any): JSX.Element {
    const [deleteModal, setDeleteModal, deletePayload, setDeletePayload, acceptCallback] = props.hooks;

    function accept() {
        acceptCallback(deletePayload);
        setDeletePayload("");
        setDeleteModal(false);
    }
    function decline() {
        setDeleteModal(false);
    }
    return (
        <div className="DeleteConfirmation">
            <Modal firstHook={deleteModal} secondHook={setDeleteModal}>
                <Content>
                    <p>האם אתה בטוח שברצונך למחוק?</p>
                    <div onClick={accept}>כן</div>
                    <div onClick={decline}>לא</div>
                </Content>
            </Modal>
        </div>
    );
}

export default DeleteConfirmation;
