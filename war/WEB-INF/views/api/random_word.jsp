<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<result>
	<response> 
		<status>${repsonse.status}</status>
		<message>${repsonse.message}</message>
		<datasize>${repsonse.size}</datasize>
	</response>
	
<weekday_topics>
<c:forEach var="random_word" items="${random_words}">
	<weekday_topic>
			<id>${random_word.week_day_topic.id}</id>
			<date>${random_word.week_day_topic.topic_date}</date>
			<topic><![CDATA[${random_word.week_day_topic.topic_word}]]></topic>
			<definition><![CDATA[${random_word.week_day_topic.topic_definition}]]></definition>
			<imageurl><![CDATA[${random_word.week_day_topic.topic_image_url}]]></imageurl>
			<quote_en><![CDATA[${random_word.week_day_topic.quote_en}]]></quote_en>
			<quote_cn><![CDATA[${random_word.week_day_topic.quote_cn}]]></quote_cn>
			<weely_topic_id>${random_word.week_day_topic.weekly_topic_id}</weely_topic_id>
			<words>
			<c:forEach var="word" items="${random_word.words}">
			    <entry>
					<id>${word.id}</id>
					<word><![CDATA[${word.word}]]></word>
					<definition><![CDATA[${word.defintion}]]></definition>
				</entry>
			</c:forEach>
			</words>
	</weekday_topic>
</c:forEach>
</weekday_topics>
</result>