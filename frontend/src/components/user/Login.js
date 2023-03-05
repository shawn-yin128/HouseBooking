import {useRef, useState} from "react";
import {login, register} from "../../utils";
import {Button, Checkbox, Form, Input, message, Space} from "antd";
import {UserOutlined, KeyOutlined} from "@ant-design/icons";

function Login(props) {
    const [asHost, setAsHost] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    const formRef = useRef();

    const handleLogin = async () => {
        const curForm = formRef.current;
        try {
            await curForm.validateFields();
        } catch (error) {
            return;
        }

        setIsLoading(true);
        try {
            const resp = await login(curForm.getFieldsValue(true), asHost);
            props.handleLogin(resp.token, asHost);
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    const handleRegister = async () => {
        const curForm = formRef.current;
        try {
            await curForm.validateFields();
        } catch (error) {
            return;
        }

        setIsLoading(true);
        try {
            await register(curForm.getFieldsValue(true), asHost);
            message.success("Register successfully.");
        } catch (error) {
            message.error(error.messages);
        } finally {
            setIsLoading(false);
        }
    };

    const handleChange = (e) => {
        setAsHost(e.target.checked);
    };

    const handleFinish = () => {
        console.log("finish form.");
    };

    return (
        <div className="login_page">
            <Form ref={formRef} onFinish={handleFinish}>
                <Form.Item label="username" name="username"
                           rules={[{required: true, message: "Please input your username."}]}>
                    <Input disabled={isLoading} prefix={<UserOutlined className="site-form-item-icon"/>}
                           placeholder="Username"/>
                </Form.Item>

                <Form.Item label="password" name="password"
                           rules={[{required: true, message: "Please input your password."}]}>
                    <Input.Password disabled={isLoading} prefix={<KeyOutlined className="site-form-item-icon"/>}
                                    placeholder="Password"/>
                </Form.Item>
            </Form>
            <Space>
                <Checkbox disabled={isLoading} checked={asHost} onChange={handleChange}>Host</Checkbox>
                <Button disabled={isLoading} shape="round" type="primary" onClick={handleLogin}>Log in</Button>
                <Button disabled={isLoading} shape="round" type="primary" onClick={handleRegister}>Register</Button>
            </Space>
        </div>
    )
}

export default Login;