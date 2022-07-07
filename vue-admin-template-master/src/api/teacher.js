import request from '@/utils/request'

export default {
    //分页查询 无条件
    getPage(num, size) {
        return request({
            url: `/edu/teacher/${num}/${size}`,
            method: 'get'
        })
    },
    //待条件查询 分页
    getPageByWrapper(num, size, teacherConditionVO) {
        return request({
            url: `/edu/teacher/queryTeacherPageByCondition/${num}/${size}`,
            method: 'get',
            params: teacherConditionVO
        })
    },
    //删除讲师
    removeById(teacherId) {
        return request({
            url: `/edu/teacher/${teacherId}`,
            method: 'delete',
        })
    },
    //添加讲师
    saveTeacher(teacher) {
        return request({
            url: "/edu/teacher",
            method: 'post',
            params: teacher
        })
    },
    //修改讲师
    modifyTeacherById(eduTeacher) {
        return request({
            url: `/edu/teacher`,
            method: 'put',
            params: eduTeacher
        })
    },
    //根据id查询讲师
    getTeacherById(id) {
        return request({
            url: `/edu/teacher/${id}`,
            method: 'get',
        })
    }
}