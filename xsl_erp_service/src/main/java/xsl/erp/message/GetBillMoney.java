package xsl.erp.message;

import com.xsl.erp.mapper.XslInputBillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import xsl.erp.commons.JedisClient;
import xsl.erp.pojo.XslInputBill;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.UUID;

public class GetBillMoney implements MessageListener {

    @Resource
    private XslInputBillMapper inputBillMapper;

    @Value("MONEY_BASE_KEY")
    private String MONEY_BASE_KEY;

    @Resource
    private JedisClient jedisClient;

    Logger logger = LoggerFactory.getLogger(GetBillMoney.class);

    @Override
    public void onMessage(Message message) {
        //取出消息内容
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            Double inputMoney = new Double(text);
            String money = jedisClient.get(MONEY_BASE_KEY);
            Double newMoney = new Double(money);

            Double countMoney = newMoney+inputMoney;

            logger.info("countMoney is {}", countMoney);
            jedisClient.set(MONEY_BASE_KEY, countMoney+"");
            //存入资金流入表
            XslInputBill inputBill = new XslInputBill();
            inputBill.setInputid(UUID.randomUUID().toString());
            inputBill.setInputMoney(inputMoney);
            inputBill.setTradetime(new Date());
            int i;
            try {
                i = inputBillMapper.insert(inputBill);
            }catch (Exception e){
                throw e;
            }
            logger.info("数据条数 i 是 {}"+i);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
