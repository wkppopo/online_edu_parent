import request from '@/utils/request'
export default {
    //查询所有讲师
    getTeacherList() {
        return request({
            url: `/edu/teacher`,
            method: 'get'
        })
    },
     //查询所有的课程
     getAllSubject() {
        return request({
            url: `/edu/subject/getAllSubject`,
            method: 'get'
        })
    },
    //保存课程信息
    saveCourseInfo(courseInfoVO) {
        return request({
            url: `/edu/course/saveCourseInfo`,
            method: 'post',
            params:courseInfoVO
        })
    },
    //带条件分页查询
    queryCoursePageByCondition(pageNum,pageSize,courseCondition) {
        return request({
            url: `/edu/course/queryCoursePageByCondition/${pageNum}/${pageSize}`,
            method: 'get',
            params: courseCondition
        })
    },
    //执行修改课程
    updateCourseInfo(courseInfoVo) {
        return request({
            url: `/edu/course/updateCourseInfo`,
            method: 'put',
            params: courseInfoVo
        })
    },
    //根据课程id查询课程信息
    getCourseById(id) {
        return request({
            url: `/edu/course/${id}`,
            method: 'get',
        })
    },
    //修改课程信息
    updateCourseInfo(courseInfoVo) {
        return request({
            url: `/edu/course/updateCourseInfo`,
            method: 'put',
            params:courseInfoVo
        })
    },
    //查询课程发布确认信息
    queryCourseConfirmInfo(courseId) {
        return request({
            url: `/edu/course/queryCourseConfirmInfo/`+courseId,
            method: 'get',
        })
    },
    //6.课程的发布
    publishCourse(courseId) {
        return request({
            url: `/edu/course/publishCourse/`+courseId,
            method: 'get',
        })
    },
    //9.删除课程
    deleteCourseById(courseId) {
        return request({
            url: '/edu/course/deleteCourseById/'+courseId,
            method: 'delete'
        })
    },
}