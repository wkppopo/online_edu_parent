import course from '@/api/course/course'

export default {
    data() {
        return {
            pageNum:1,
            pageSize:4,
            total:0,
            //课程列表
            courseList:null,
            //课程查询条件
            courseCondition:{
                title:'',
                status:''
            }
        }
    },
    methods: {
        //1.分页查询课程信息带条件
        queryCoursePageByCondition(pageNumUI=1) {
            this.pageNum=pageNumUI;
            course.queryCoursePageByCondition(this.pageNum, this.pageSize, this.courseCondition)
            .then(response=>{
                this.total=response.data.total;
                this.courseList=response.data.courseList;
            })
        },
        //清空课程列表栏数据
        resetData(){
            this.courseCondition.title="";
            this.courseCondition.status="";
        },
        //根据课程id删除 课程 和所有 章节 和 小节
        deleteCourseById(courseId){
            this.$confirm("确定下此狠手?", "提示", {
                confirmButtonText: "确定干",
                cancelButtonText: "取消",
                type: "warning"
            })
                .then(() => {
                    course.deleteCourseById(courseId)
                        .then(response => {
                            this.$message({
                                type: "success",
                                message: "删除成功!"
                            });
                            this.queryCoursePageByCondition();
                        })
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "取消删除"
                    });
                });
        }
    },
    created() {
        this.queryCoursePageByCondition();
    }
}