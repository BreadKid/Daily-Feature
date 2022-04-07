# Daily-Feature
开发常用功能实现

### Base Tech Stack：
* JDK：```17.0.2```
* Framework：```SpringBoot 2.6.3```
* Dependency：```Gradle 7.4.1```

### Feature List:
* [x] 带缓存图验API：
  - 返回Base64字符串
  - 写入HttpServletResponse，直接返回图片
* [ ] Excel导入导出
  - POI
  - EasyExcel
* [ ] 高性能时间处理
* [x] [脱敏正则](src/test/java/com/breadykid/dailyfeature/DataMaskUtilTest.java)
* [ ] JWT生成及解析
* [ ] 限流算法
  - [] RateLimiter单机限流
* [ ] Stream集合操作优雅实现
  - 交集
  - 并集
  - 差集
