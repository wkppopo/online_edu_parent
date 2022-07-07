import request from '@/utils/request'
export default {
    //查询所有的课程
    getAllSubject() {
        return request({
            url: `/edu/subject/getAllSubject`,
            method: 'get'
        })
    },
    //删除节点信息
    deleteSubjectById(subjectId) {
        return request({
            url: `/edu/subject/` + subjectId,
            method: 'delete'
        })
    },

    //保存一级分类节点信息
    savParentSubject(subject) {
        return request({
            url: `/edu/subject/savParentSubject`,
            method: 'post',
            params: subject
        })
    },
    //保存二级分类节点信息
    savChildSubject(subject) {
        return request({
            url: `/edu/subject/savChildSubject`,
            method: 'post',
            params: subject
        })
    },
}