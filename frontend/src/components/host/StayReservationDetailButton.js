import {useState} from "react";
import {Button, Modal} from "antd";
import StayReservationList from "./StayReservationList";

function StayReservationDetailButton(props) {
    const [visible, setVisible] = useState(false);
    const {stay} = props;

    const handleOpen = () => {
        setVisible(true);
    };

    const handleCancel = () => {
        setVisible(false);
    };

    return (
        <>
            <Button size="small"
                    shape="round"
                    onClick={handleOpen}>
                View Reservations
            </Button>
            {visible && (
                <Modal title={`Reservations of ${stay.name}`}
                       centered={true}
                       open={visible}
                       closable={true}
                       footer={null}
                       onCancel={handleCancel}
                       destroyOnClose={true}>
                    <StayReservationList stayId={stay.id}/>
                </Modal>
            )}
        </>
    )
}

export default StayReservationDetailButton;