import course from "@/api/course/course"
import Tinymce from "@/components/Tinymce"

const defaultCourseInfoVo = {
    //讲师id
    teacherId: '',
    parentSubjectId: "",
    subjectId: "",
    //课程封面信息
    cover: "/static/金克斯.jpg",
    title: '',
    price: 0,
    lessonNum: 0,
    description: ''
}
export default {
    //声明组件
    components: {
        Tinymce
    },

    data() {
        return {
            //所有讲师集合
            teacherList: [],
            ParentSubjectList: [],
            childSubjectList: [],
            saveBtnDisabled: false,
            //后端接口接受的参数
            courseInfoVo: defaultCourseInfoVo,
            BASE_API: process.env.BASE_API,
            courseId: ""
        }

    },
    watch: {
        //监听内置对象$route :通常用于修改页面转发到保存页面时，页面不刷新而携带回显数据。清空处理
        $route(to, from) {
            //路由有变化时从新对初始值进行赋值
            this.courseInfoVo = defaultCourseInfoVo;
        }
    },
     methods: {
        //获取讲师列表集合
        getTeacherList() {
            course.getTeacherList().then(response => {
                this.teacherList = response.data.teacherList;
                console.log(this.teacherList);
            })
        },
        //获取所有的课程
        getAllSubject() {
            course.getAllSubject().then(response => {
                this.ParentSubjectList = response.data.allSubject;
            })
        },
        //当选择课程一级分类时@change，获取一级分类id，然后获取相应的二级分类
        getChildSubject(currentNodeId) {
            for (let index = 0; index < this.ParentSubjectList.length; index++) {
                const parentSubject = this.ParentSubjectList[index];
                if (parentSubject.id === currentNodeId) {
                    this.childSubjectList = parentSubject.children;
                }
            }
        },
        //4.上传之前调用的方法
        beforeUploadCover(file) {
            const isJPG = file.type === "image/jpeg";
            const isLittleThan2M = file.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error("上传头像图片只能是 JPG 格式!");
            }
            if (!isLittleThan2M) {
                this.$message.error("上传头像图片大小不能超过 2MB!");
            }
            return isJPG && isLittleThan2M;
        },
        //4.上传封面成功之后
        afterUploadCover(res, file) {
            //retVal ：是后端返回的连接
            this.courseInfoVo.cover = res.data.retVal;
        },
        //保存课程信息
        saveCourseInfo() {
            course.saveCourseInfo(this.courseInfoVo).then(response => {
                //1.弹窗提示
                this.$message({
                    message: "添加成功",
                    type: "success"
                })
                this.courseId=response.data.courseId;
                //2.跳转到指定页面
                this.$router.push({ path: "/course/chapter/"+this.courseId });
            })
        },
        //根据id查询课程信息
        getCourseById() {
            course.getCourseById(this.courseId).then(res => {
                this.courseInfoVo = res.data.courseInfoVo;
                this.getTeacherList();
                //获取所有的课程信息 
                course.getAllSubject().then(response => {
                    this.ParentSubjectList = response.data.allSubject;
                    //遍历所有课程
                    for (let index = 0; index < this.ParentSubjectList.length; index++) {
                        const parentSubject = this.ParentSubjectList[index];
                        if (this.courseInfoVo.parentSubjectId === parentSubject.id) {
                            this.childSubjectList = parentSubject.children;
                        }
                    }
                })
            })
        },
        //修改课程信息
        updateCourseInfo() {
            course.updateCourseInfo(this.courseInfoVo).then(response => {
                //1.弹窗提示
                this.$message({
                    message: "添加成功",
                    type: "success"
                });
                //2.跳转到指定页面
                this.$router.push({ path: "/course/chapter/"+this.courseId });
            })
        },
        //修改或保存信息
        saveOrUpdateCourse() {
            if (this.courseId) {
                this.updateCourseInfo();
            } else {
                //保存课程信息
                this.saveCourseInfo();
            }
        }
    },
    created() {
        this.courseId = this.$route.params.courseId;
        if (this.courseId) {
            this.getCourseById();
        }else{
            //this.courseInfoVo = defaultCourseInfoVo
            this.getTeacherList();
            this.getAllSubject();
        }
    }
}