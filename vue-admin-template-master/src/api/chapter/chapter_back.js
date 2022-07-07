import request from '@/utils/request'
export default {
    //1.章节和小节的列表查询
    getChapterAndSection(courseId) {
        return request({
            url: '/edu/chapter/getChapterAndSection/' + courseId,
            method: 'get'
        })
    },
    //2.添加章节
    saveChapter(chapter) {
        return request({
            url: '/edu/chapter/',
            method: 'post',
            params: chapter
        })
    },
    //3.根据章节id查询章节信息
    getChapterById(id) {
        return request({
            url: '/edu/chapter/getChapter/' + id,
            method: 'get'
        })
    },
    //4.更新章节信息
    updateChapter(chapter) {
        return request({
            url: '/edu/chapter/',
            method: 'put',
            params: chapter

        })
    },
    //5.删除章节信息
    deleteChapter(id) {
        return request({
            url: '/edu/chapter/' + id,
            method: 'delete'
        })
    },
}