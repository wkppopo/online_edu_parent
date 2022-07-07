<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="课程确认" />
    </el-steps>

    <el-button @click="saveChapterDialog()" type="text">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterAndSectionList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button @click="saveSectionDialog(chapter.id)" type="text">添加小节</el-button>
            <el-button @click="updateChapterDialog(chapter.id)" type="text">修改</el-button>
            <el-button @click="deleteChapter(chapter.id)" type="text">删除</el-button>
          </span>
        </p>

        <!-- 小节 -->
        <ul class="chanpterList videoList">
          <li v-for="section in chapter.children" :key="section.id">
            <p>
              {{section.title}}
              <span class="acts">
                <el-button type="text" @click="updateSectionDialog(section.id)">修改</el-button>
                <el-button type="text" @click="deleteSection(section.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <div>
      <el-button @click="previous()">上一步</el-button>
      <el-button @click="next()" type="primary">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="chapterFormVisible" title="添加章节">
      <el-form :model="chapterUI" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapterUI.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapterUI.sort" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="chapterFormVisible = false">取 消</el-button>
        <el-button @click="saveOrUpdateChapter()" :disabled="saveChapterBtnDisabled" type="primary">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改小节表单 -->
    <el-dialog :visible.sync="sectionFormVisible" title="添加小节">
      <el-form :model="sectionUI" label-width="120px">
        <el-form-item label="小节标题">
          <el-input v-model="sectionUI.title"/>
        </el-form-item>
        <el-form-item label="小节排序">
          <el-input-number v-model="sectionUI.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="sectionUI.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- 上传视频 -->
        <el-form-item label="上传视频">
          <el-upload
            :on-success="uploadVideoSuccess"
            :on-remove="deleteVideo"
            :before-remove="beforeVideoDelete"
            :on-exceed="uploadExceed"
            :file-list="fileList"
            :action="'http://127.0.0.1:8003/aliyun/uploadAliyunVideo'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，
                <br>支持3GP、ASF、AVI、DAT、DV、FLV、F4V、
                <br>GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、
                <br>MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、
                <br>SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="sectionFormVisible = false">取 消</el-button>
        <el-button @click="saveOrUpdateSection()" :disabled="saveSectionBtnDisabled" type="primary">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script src="@/api/chapter/chapter.js"></script>

<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}

.chanpterList li {
  position: relative;
}

.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}

.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}

.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}
</style>
	