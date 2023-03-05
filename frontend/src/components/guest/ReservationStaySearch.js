import {useState} from "react";
import {searchStays} from "../../utils";
import {Button, Card, Carousel, DatePicker, Form, Input, InputNumber, List, message, Image} from "antd";
import Text from "antd/lib/typography/Text";
import StayDetailButton from "../host/StayDetailButton";
import ReservationAddButton from "./ReservationAddButton";

function ReservationStaySearch() {
    const [isLoading, setIsLoading] = useState(false);
    const [data, setData] = useState([]);

    const handleFinish = async (query) => {
        setIsLoading(true);
        console.log(query);

        try {
            const resp = await searchStays(query);
            setData(resp);
            console.log(data);
        } catch (error) {
            message.error(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <>
            <Form onFinish={handleFinish} layout="inline">
                <Form.Item label="Guest Number" name="guest_number" rules={[{required: true}]}>
                    <InputNumber min={1}/>
                </Form.Item>
                <Form.Item label="Date Range" name="date_range" rules={[{required: true}]}>
                    <DatePicker.RangePicker/>
                </Form.Item>
                <Form.Item label="Address" name="address" rules={[{required: true}]}>
                    <Input/>
                </Form.Item>
                <Form.Item label="Distance(KM)" name="distance" rules={[{required: false}]}>
                    <InputNumber/>
                </Form.Item>
                <Form.Item>
                    <Button loading={isLoading} type="primary" htmlType="submit">
                        Submit
                    </Button>
                </Form.Item>
            </Form>
            <List className="reservation_list"
                  loading={isLoading}
                  grid={{gutter: 16, xs: 1, sm: 3, md: 3, lg: 3, xl: 4, xxl: 4}}
                  dataSource={data}
                  renderItem={(item) =>
                      <List.Item>
                          <Card key={item.id}
                                title={
                                    <div className="reservation_list_card_title">
                                        <Text ellipsis={true} className="reservation_list_card_title_content">
                                            {item.name}
                                        </Text>
                                        <StayDetailButton stay={item}/>
                                    </div>
                                }
                                extra={<ReservationAddButton stay={item}/>}>
                          </Card>
                          <Carousel autoplay={true}>
                              {item.images.map((image, index) =>
                                  <div key={index}>
                                      <Image src={image.url} width="100%"/>
                                  </div>
                              )}
                          </Carousel>
                      </List.Item>
                  }/>
        </>
    )
}

export default ReservationStaySearch;