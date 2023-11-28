package com.example.view;

import com.example.service.FileUploadService;
import org.hibernate.service.spi.InjectService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedBean(name = "fileUploadView")
@SessionScoped
@Controller
public class FileUploadView {
    @Autowired
    private FileUploadService fileUploadService;

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() throws IOException {
        System.out.println("File uploaded: " + file.getFileName());

        fileUploadService.saveFileToDatabase(file);
    }

    public void fileUpload(FileUploadEvent event) throws IOException {
        String path = FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        String name = fmt.format(new Date())
                + event.getFile().getFileName().substring(
                event.getFile().getFileName().lastIndexOf('.'));
        File file = new File(path + "catalogo_imagens/temporario/" + name);

        InputStream is = event.getFile().getInputStream();
        OutputStream out = new FileOutputStream(file);
        byte buf[] = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0)
            out.write(buf, 0, len);
        is.close();
        out.close();
    }
}
