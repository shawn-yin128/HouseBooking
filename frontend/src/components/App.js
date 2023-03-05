import {Layout, Dropdown, Button, Space, Menu} from "antd";
import {UserOutlined} from "@ant-design/icons";
import {useEffect, useState} from "react";
import Login from "./user/Login"
import HostHome from "./host/HostHome";
import GuestHome from "./guest/GuestHome";

const {Header, Content, Footer} = Layout;

function App() {
    const [authed, setAuthed] = useState(false);
    const [asHost, setAsHost] = useState(false);

    useEffect(() => {
        const curAuth = localStorage.getItem("Token") !== null;
        const curRole = localStorage.getItem("asHost") === "true";
        setAuthed(curAuth);
        setAsHost(curRole);
    }, [])

    const handleLogin = (token, asHost) => {
        localStorage.setItem("Token", token);
        localStorage.setItem("asHost", asHost);
        setAuthed(true);
        setAsHost(asHost);
    }

    const handleLogout = () => {
        localStorage.removeItem("Token");
        localStorage.removeItem("asHost");
        setAuthed(false);
    }

    const UserMenu = () => {
        return (
            <Menu>
                <Menu.Item key="logout" onClick={handleLogout}>
                    Log Out
                </Menu.Item>
            </Menu>
        );
    };

    return (
        <Layout className="layout">
            <Header className="header">
                <div className="App-title">House Booking</div>
                {authed &&
                    (<div>
                        <Dropdown trigger="click" overlay={UserMenu}>
                            <Space>
                                <div className="App-title">Profile</div>
                                <Button icon={<UserOutlined/>} shape="circle"/>
                            </Space>
                        </Dropdown>
                    </div>)
                }
            </Header>
            <Content className="content">
                {!authed ?
                    <Login handleLogin={handleLogin}/>
                    : asHost ?
                        <HostHome/>
                        : <GuestHome/>
                }
            </Content>
            <Footer className="footer">
                House Booking ©2023 Created by S.Y
            </Footer>
        </Layout>
    );
}

export default App;