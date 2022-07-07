<template>
    <div class="app-container">
        <el-form label-width="120px">
            <el-form-item label="讲师名称">
                <el-input v-model="teacherParam.name" />
            </el-form-item>
            <el-form-item label="讲师排序">
                <el-input v-model="teacherParam.sort" controls-position="right" min="0" />
            </el-form-item>
            <el-form-item label="讲师头衔">
                <el-select v-model="teacherParam.level" clearable placeholder="请选择">
                    <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
                    <el-option :value="1" label="高级讲师" />
                    <el-option :value="2" label="首席讲师" />
                </el-select>
            </el-form-item>
            <el-form-item label="讲师资历">
                <el-input v-model="teacherParam.career" />
            </el-form-item>
            <el-form-item label="讲师简介">
                <el-input v-model="teacherParam.intro" :rows="10" type="textarea" />
            </el-form-item>

            <!-- 讲师头像：TODO -->
            <!-- 讲师头像 -->
            <el-form-item label="讲师头像">
                <!-- 头衔缩略图 -->
                <pan-thumb :image="teacherParam.avatar" />
                <!-- 文件上传按钮 -->
                <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow = true">更换头像</el-button>

                <!--
                v-show：是否显示上传组件
                :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                :url：后台上传的url地址
                @close：关闭上传组件
                @crop-upload-success：上传成功后的回调
                field: 属性必须和后端的方法参数一致（MultipartFile file） 
                -->
                <image-cropper v-show="imagecropperShow" :width="300" :height="300" :key="imagecropperKey"
                    :url="BASE_API + '/oss/uploadFile'" field="file" @close="close"
                    @crop-upload-success="cropSuccess" />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="saveOrUpdateTeacher()">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import teacher from "@/api/teacher";
import PanThumb from "@/components/PanThumb";
import ImageCropper from "@/components/ImageCropper";

const defaultTeacherForm = {
    name: "",
    sort: 0,
    level: "",
    career: "",
    intro: "",
    avatar: ""
}

export default {
    //声明组件
    components: {
        PanThumb, ImageCropper
    },
    data() {
        return {
            teacherParam: defaultTeacherForm,
            //是否显示上传组件
            imagecropperShow: false,
            //上传组件id
            imagecropperKey: 0,
            BASE_API: process.env.BASE_API
        };
    },
    watch: {
        //监听内置对象$route
        $route(to, from) {
            debugger;
            //路由有变化时从新对初始值进行赋值
            this.teacherParam = defaultTeacherForm;
        }
    },
    methods: {
        //保存讲师
        saveTeacher() {
            teacher.saveTeacher(this.teacherParam).then(response => {
                //添加成功后弹框提示
                this.$message({
                    message: "添加成功",
                    type: "success"
                });
                //添加成功后跳转到list页面
                this.$router.push({ path: "/teacher/list" });
            })
        },
        //根据Id查询讲师
        getTeacherById(teacherId) {
            teacher.getTeacherById(teacherId).then(response => {
                this.teacherParam = response.data.teacher;
            })
        },

        //修改讲师
        modifyTeacherById() {
            teacher.modifyTeacherById(this.teacherParam).then(response => {
                //弹框提示添加成功;
                this.$message({
                    message: "修改成功",
                    type: "success"
                });
                //使用内置的路由对象 跳转到讲师列表页面
                this.$router.push({ path: "/teacher/list" });
            })
        },
        //添加或者修改讲师
        saveOrUpdateTeacher() {
            if (this.$route.params.teacherId) {
                this.modifyTeacherById();
            } else {
                this.saveTeacher();
            }
        },
        //关闭头像组件
        close() {
            this.imagecropperShow = false;
            //改变上传组件的id值
            this.imagecropperKey = this.imagecropperKey + 1;
        },
        //上传成功后
        cropSuccess(response) {
            this.teacherParam.avatar=response.retVal;
            this.imagecropperShow = false;
            //改变上传组件的id值
            this.imagecropperKey = this.imagecropperKey + 1;
        },
        init0(){
            this.teacherParam
        }
    },
    created() {
        //根据地址栏是否有id来判读是否执行回显
        //这个方法是，从修改页面中断操作，后直接跳转到（转发）保存页面时，根据地址栏是否携带id决定是否需要初始化
        if (this.$route.params.teacherId) {
            //this.$route.params.teacherId ：获取地址栏的id参数
            this.getTeacherById(this.$route.params.teacherId);
            debugger;
        }
    },
    
}
</script>
