import {useState} from "react";
import {addReservation} from "../../utils";
import {Button, DatePicker, Form, message, Modal} from "antd";

function ReservationAddButton(props) {
    const [isLoading, setIsLoading] = useState(false);
    const [visible, setVisible] = useState(false);
    const {stay} = props;

    const handleOpen = () => {
        setVisible(true);
    };

    const handleCancel = () => {
        setVisible(false);
    };

    const handleSubmit = async (values) => {
        setIsLoading(true);

        try {
            await addReservation({
                checkin_date: values.date_range[0].format("YYYY-MM-DD"),
                checkout_date: values.date_range[1].format("YYYY-MM-DD"),
                stay: {
                    id: stay.id
                }
            });
            message.success("Book Successfully.");
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <>
            <Button onClick={handleOpen} shape="round" type="primary">
                Book Stay
            </Button>
            <Modal title={stay.name}
                   open={visible}
                   footer={null}
                   closable={true}
                   onCancel={handleCancel}>
                <Form preserve={false}
                      labelCol={{span: 8}}
                      wrapperCol={{span: 16}}
                      onFinish={handleSubmit}>
                    <Form.Item label="Reserve Date" name="date_range" rules={[{required: true}]}>
                        <DatePicker.RangePicker/>
                    </Form.Item>
                    <Form.Item wrapperCol={{offset: 8, span: 16}}>
                        <Button loading={isLoading}
                                type="primary"
                                htmlType="submit">
                            Book
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}

export default ReservationAddButton;