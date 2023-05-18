import { Tooltip } from "@mui/material";
import "./CouponRow.css";
import { CompanyCouponsStore, deleteCoupon, fetchCoupons, updateCoupon } from "../../../../../../Store/CompanyCouponsState";
import CouponItem from "../../../../../../Models/Coupon/CouponItem";
import CompanyService from "../../../../../../Services/CompanyService";

function CouponRow(props:any): JSX.Element {
    const [setOpen, setEditModal, setImageModal, setCurrentDelete, setSelectedCoupon] = props.hooks;
    const couponId = props.info?.id;
    let theCoupon: CouponItem;
    function loadCoupon(){
        new CompanyService("2").getOneCoupon(couponId).then(coupon => {
            theCoupon = coupon;
        }).catch(err => console.log(err));
    }
    function changeImageAction() {
        new CompanyService("2").getOneCoupon(couponId).then(coupon => {
            setSelectedCoupon(coupon);
            setImageModal(true);
            }).catch(err => console.log(err));
       
        // setCurrentDelete(props.info?.id);
    }
    function updateSelected(){
        new CompanyService("2").getOneCoupon(couponId).then(coupon => {
            setEditModal(true);
            setCurrentDelete(props.info?.id);
            setSelectedCoupon(coupon);
        }).catch(err => console.log(err));
    }
    function editAction() {
        updateSelected();
        // CompanyCouponsStore.dispatch(updateCoupon(props.info));
        // setTableItems(CompanyCouponsStore.getState().coupons);

        // setCompanyInfo(props.info);
        // setUpdateModal(true);
    }
    function deleteAction() {
        setOpen(true);
        setCurrentDelete(props.info?.id);
        // CompanyCouponsStore.dispatch(deleteCoupon(props.info?.id));
        // deleteRow(props.info?.id);
        
        // CompanyCouponsStore.dispatch(deleteCoupon(props.info?.id));
        // setTableItems(CompanyCouponsStore.getState().coupons);
        // setDeletePayload(props.info.id);
        // setDeleteModal(true);
    }
    loadCoupon();
    return (
        <tr className="CouponRow">
			<td>{props.info?.id}</td>
            <td>{props.info?.title}</td>
            <td>{props.info?.description}</td>
            <td>{props.info?.category}</td>
            <td>{props.info?.startDate}</td>
            <td>{props.info?.endDate}</td>
            <td>{props.info?.amount}</td>
            <td>{"₪" + props.info?.price}</td>


            <td>
                <Tooltip title="שינוי תמונה">
                    <span className="material-symbols-outlined icon-hover password" onClick={changeImageAction}>image</span>
                </Tooltip>
                <Tooltip title="עריכה">
                    <span className="material-symbols-outlined icon-hover edit" onClick={editAction}>tune</span>
                </Tooltip>
                <Tooltip title="מחיקה">
                    <span className="material-symbols-outlined icon-hover delete" onClick={deleteAction}>delete</span>
                </Tooltip>
            </td>
        </tr>
    );
}

export default CouponRow;
