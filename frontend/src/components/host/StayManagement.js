import {useEffect, useState} from "react";
import {getStaysByHost} from "../../utils";
import {Card, Carousel, Image, List, message} from "antd";
import Text from "antd/lib/typography/Text"
import StayDetailButton from "./StayDetailButton";
import StayRemoveButton from "./StayRemoveButton";
import StayReservationDetailButton from "./StayReservationDetailButton";

function StayManagement() {
    const [isLoading, setIsLoading] = useState(false);
    const [data, setData] = useState([]);

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        setIsLoading(true);
        try {
            const resp = await getStaysByHost();
            setData(resp);
        } catch (error) {
            message.error(error.messages);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <List loading={isLoading}
              grid={{gutter: 16, xs: 1, sm: 3, md: 3, lg: 3, xl: 4, xxl: 4}}
              dataSource={data}
              renderItem={(item) => (
                  <List.Item>
                      <Card key={item.id}
                            title={
                                <div className="stay_management_card">
                                    <Text ellipsis={true} className="stay_management_text">
                                        {item.name}
                                    </Text>
                                    <StayDetailButton stay={item}/>
                                </div>
                            }
                            actions={[<StayReservationDetailButton stay={item}/>]}
                            extra={<StayRemoveButton stay={item} onRemove={loadData}/>}>
                          <Carousel autoplay={true}>
                              {item.images.map((image, index) =>
                                  <div key={index}>
                                      <Image src={image.url} width="100%"/>
                                  </div>
                              )}
                          </Carousel>
                      </Card>
                  </List.Item>
              )}/>
    )
}

export default StayManagement;