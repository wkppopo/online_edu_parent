package com.atguigu.edu.listener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.entity.SubjectExcel;
import com.atguigu.edu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SubjectListener extends AnalysisEventListener<SubjectExcel> {

    @Autowired
    private EduSubjectService subjectService;

    /**
     *  下面这个方法，有多条sql语句同时操作数据库表，所以需要加上事务注解@Transactional
     */
    @Transactional
    @Override
    public void invoke(SubjectExcel subjectExcel, AnalysisContext analysisContext) {
        //subjectExcel包含两列的数据 第一列是一级分类 第二列是二级分类
        String parentSubjectName = subjectExcel.getParentSubjectName();
        //保存一级分类的时候 需要判断该分类在数据库中是否存在 parent_id=0
        EduSubject parentSubject = subjectService.existSubject(parentSubjectName, "0");
        if(parentSubject==null){
            parentSubject=new EduSubject();
            parentSubject.setTitle(parentSubjectName);
            parentSubject.setParentId("0");
            subjectService.save(parentSubject);
        }
        String childSubjectName = subjectExcel.getChildSubjectName();
        //保存二级分类的时候 需要判断该分类在数据库中是否存在 parent_id=一级分类的id  useGenerateKeys
        EduSubject childSubject = subjectService.existSubject(childSubjectName, parentSubject.getId());
        if(childSubject==null){
            childSubject=new EduSubject();
            childSubject.setTitle(childSubjectName);
            childSubject.setParentId(parentSubject.getId());
            subjectService.save(childSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
