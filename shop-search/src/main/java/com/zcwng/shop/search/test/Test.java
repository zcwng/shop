package com.zcwng.shop.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

public class Test {

	public static void main(String[] args) throws SolrServerException, IOException {
		//1.创建连接对象
/*		String url="http://localhost:8983/solr/shop";
		Builder builder = new HttpSolrClient.Builder(url);
		HttpSolrClient client = builder.build();
		
		//2.创建一个文档对象
		SolrInputDocument doc = new SolrInputDocument();
		//向文档中添加域以及对应的值,注意：所有的域必须在schema.xml中定义过,前面已经给出过我定义的域。
		doc.addField("item_title", " 南极人 女士内裤女纯棉档内衣女性感蕾丝中腰收腹提臀三角裤女式内裤 浅紫+浅绿+浅粉 均码");
		doc.addField("item_sell_point", "1.柔情蕾丝，淡雅色调，性感朦胧,2.优质面料，柔软亲肤，健康呵护 3.精致绣花蕾丝，透气网纱,4.弹力裤腰，不紧勒，耐久经穿点击查看更多(此商品不参加上述活动)");
		doc.addField("item_price", 59.00);
		doc.addField("item_category_name", " 南极人（Nanjiren）");
		doc.addField("item_desc", "商品名称：南极人女士内裤商品编号：100002645443商品毛重：62.00g商品产地：中国浙江台州尺码：均码风格：性感面料：锦纶组合形式：3条装颜色：多色图案：花朵腰型：中腰类别：三角裤功能：速干适用人群：青少年上市时间：2019年春季");
		//3.将文档写入索引库中
		client.add(doc);
		//提交
		client.commit();
*/
	}
}
