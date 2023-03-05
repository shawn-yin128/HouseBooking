import {useRef, useState} from "react";
import {Button, Form, Input, InputNumber, message} from "antd";
import {uploadStay} from "../../utils";

function StayUpload() {
    const [isLoading, setIsLoading] = useState(false);
    const fileRef = useRef(null);

    const handleFinish = async (values) => {
        const formData = new FormData();
        const {files} = fileRef.current;

        if (files.length > 5) {
            message.error("Picture upload max to 5.");
            return;
        }

        for (let i = 0; i < files.length; i++) {
            formData.append("images", files[i]);
        }
        formData.append("name", values.name);
        formData.append("address", values.address);
        formData.append("description", values.description);
        formData.append("guest_number", values.guest_number);

        setIsLoading(true);
        try {
            await uploadStay(formData);
            message.success("Upload Successfully.");
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Form className="stay_upload_form"
              name="nest-messages"
              labelCol={{span: 8}}
              wrapperCol={{span: 16}}
              onFinish={handleFinish}>
            <Form.Item label="Name" name="name" rules={[{required: true}]}>
                <Input/>
            </Form.Item>
            <Form.Item label="Address" name="address" rules={[{required: true}]}>
                <Input/>
            </Form.Item>
            <Form.Item label="Description" name="description" rules={[{required: true}]}>
                <Input.TextArea autoSize={{minRows: 2, maxRows: 6}}/>
            </Form.Item>
            <Form.Item label="Guest Number" name="guest_number" rules={[{required: true, type: "number", min: 1}]}>
                <InputNumber/>
            </Form.Item>
            <Form.Item label="Picture Upload" name="picture" rules={[{required: true}]}>
                <input type="file" accept="image/png, image/jpeg" ref={fileRef} multiple={true}/>
            </Form.Item>
            <Form.Item wrapperCol={{span: 16, offset: 8}}>
                <Button type="primary" htmlType="submit" loading={isLoading}>
                    Submit
                </Button>
            </Form.Item>
        </Form>
    )
}

export default StayUpload;