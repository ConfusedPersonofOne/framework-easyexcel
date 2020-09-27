excel 表导入模块使用规则：
     1. 注意LocalTimeDate的实例对象，记得加 
         @ExcelProperty(converter = LocalDateTimeConverter.class)
     2. 注意Entity对象和excel对象对应规则
         数据未映射顺序：
            eg：Entity(XH , XX)
                 继承BaseEntity：XH,XX
         数据指定映射顺序：
             @ExcelProperty(value = "" , index = "") 
             value + index 配合使用,下标0开始
      3. 特别注意：
          excel表字段 和 Entity字段映射顺序。
          1) excel表字段顺序对应就是Entity实体对象排列顺序
excel 表使用文件
      1) 继承ISave 重新saveLine方法，保存读取的对象
      2) 涉及对象，返回就是对象，非涉及对象，一定是Map<Integer , String>
eg:
  /**
     * Description 执行excel导入操作.
     * @author ZJW
     * @date 2020/09/22 11:27
     **/
    public void importExcel(File file) {
        log.info("ModelDataListener excel[有模型]导入 运行");
        ModelDataListener<Xscjxx> listener = new ModelDataListener<Xscjxx>();
        listener.setSave(iXscjxxService);
        try {
            EasyExcel.read(new FileInputStream(file) , Xscjxx.class , listener)
                    .autoTrim(true)
                    .sheet(0)
                    .doReadSync();
        } catch (Exception e) {
            throw new BaseException(e.getMessage(),e);
        }
    }
注：Xscjxx.class 不填，saveLine就是Map<Integer,String> 下标从0开始    
             
             
                 
             
     
     
    
   
