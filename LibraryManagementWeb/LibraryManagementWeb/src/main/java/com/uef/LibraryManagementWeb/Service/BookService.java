package com.uef.LibraryManagementWeb.Service;

import com.uef.LibraryManagementWeb.Model.Book;
import com.uef.LibraryManagementWeb.Repository.BookRepository;
import com.uef.LibraryManagementWeb.UsefulMethod.Exception.InformationNotFoundException;
import com.uef.LibraryManagementWeb.UsefulMethod.Exception.SystemErrorException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final Path fileStorageLocation;

    @Autowired
    public BookService(BookRepository bookRepository,
                       @Value("${file.upload-dir}") String uploadDir) {
        this.bookRepository = bookRepository;
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Không thể tạo thư mục chứa file.", ex);
        }
    }

    //Thêm sách vào DB
    //Thêm sách vào DB và lưu ảnh bìa sách
    public void addBook(Book book, MultipartFile bookCover) throws IOException {
        //Kiểm tra thông tin sách có hợp lệ không
        if (book.getBookName() == null || book.getBookName().isEmpty()
                || book.getAuthorName() == null || book.getAuthorName().isEmpty()
                || book.getBookDescription() == null || book.getBookDescription().isEmpty()
                || book.getNumberOfCopy() == null || book.getNumberOfCopy() < 0
                || book.getRentPrice() == null || book.getRentPrice() < 0) {
            throw new BadRequestException("Thông tin sách bị trống hoặc không hợp lệ");
        }

        //Lấy tên file ảnh và làm sạch tên file
        String fileName = StringUtils.cleanPath(bookCover.getOriginalFilename());
        try {
            //Kiểm tra tên file có hợp lệ không
            if (fileName.contains("..")) {
                throw new BadRequestException("Tên ảnh chứa ký tự không hợp lệ " + fileName);
            }

            //Lưu file ảnh vào thư mục chỉ định
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(bookCover.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            //Lưu đường dẫn ảnh vào book
            book.setBookCoverpath(fileName);

            //Lưu thông tin sách vào DB
            bookRepository.save(book);
        } catch (IOException ex) {
            throw new BadRequestException("Không thể lưu ảnh: " + fileName + ". Hãy thử lại!", ex);
        }
    }

    //Lấy tất cả các sách trong DB
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //Lấy tất cả các sách có trạng thái đang được mở
    public List<Book> getAllAvailableBooks() {
        List<Book> books = bookRepository.findByAvailible(true);
        if (books == null) {
            throw new InformationNotFoundException("Không tồn sách cần tìm");
        }
        return books;
    }

    //Lấy sách theo Id
    public Book getBookById(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get();
        }
        throw new InformationNotFoundException("Không tồn sách cần tìm");
    }

    //Tìm kiếm sách theo tên sách
    public List<Book> searchBookByName(String bookName) {
        List<Book> books = bookRepository.findByBookNameContainingIgnoreCase(bookName);
        if (books == null) {
            throw new InformationNotFoundException("Không tồn sách cần tìm");
        }
        return books;
    }

    //Tìm kiếm theo tên tác giả
    public List<Book> searchBookByAuthorName(String authorName) {
        List<Book> books = bookRepository.findByAuthorNameContainingIgnoreCase(authorName);
        if (books == null) {
            throw new InformationNotFoundException("Không tồn sách cần tìm");
        }
        return books;
    }

    //Xóa sách theo Id
    public void deleteBookById(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new InformationNotFoundException("Không tồn sách cần xóa");
        }
        bookRepository.deleteById(bookId);
    }

    //Cập nhật thông tin sách
    public Book updateBook(Book book, MultipartFile bookCover, String bookId) throws BadRequestException {
        //Kiểm tra thông tin sách có hợp lệ không
        if (book.getBookName() == null || book.getBookName().isEmpty()
                || book.getAuthorName() == null || book.getAuthorName().isEmpty()
                || book.getBookDescription() == null || book.getBookDescription().isEmpty()
                || book.getNumberOfCopy() == null || book.getNumberOfCopy() < 0
                || book.getRentPrice() == null || book.getRentPrice() < 0) {
            throw new BadRequestException("Thông tin sách bị trống hoặc không hợp lệ");
        }

        //Kiểm tra xem sách cần cập nhật có tồn tại không
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new InformationNotFoundException("Không tồn tại sách cần cập nhật");
        }

        Optional<MultipartFile> coverOptional = Optional.ofNullable(bookCover);
        if (coverOptional.isPresent()) {
            Book existingBook = bookOptional.get();
            String oldFileName = existingBook.getBookCoverpath();
            String newFileName = StringUtils.cleanPath(bookCover.getOriginalFilename());

            try {
                //Kiểm tra tên file ảnh có hợp lệ không
                if (newFileName.contains("..")) {
                    throw new BadRequestException("Tên ảnh chứa ký tự không hợp lệ: " + newFileName);
                }

                //xóa ảnh cũ khỏi thư mục
                if (oldFileName != null && !oldFileName.isEmpty()) {
                    Path oldFilePath = this.fileStorageLocation.resolve(oldFileName);
                    try {
                        Files.deleteIfExists(oldFilePath);
                    } catch (IOException e) {
                        throw new SystemErrorException("Không thể xóa ảnh cũ: " + oldFileName + ". Hãy thử lại!", e);
                    }
                }

                //lưu ảnh mới vào thư mục
                Path targetLocation = this.fileStorageLocation.resolve(newFileName);
                Files.copy(bookCover.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                existingBook.setBookCoverpath(newFileName);

                //Cập nhật thông tin sách vào DB
                bookRepository.save(existingBook);
                return existingBook;
            } catch (IOException ex) {
                throw new BadRequestException("Không thể lưu ảnh: " + newFileName + ". Hãy thử lại!", ex);
            }
        }
        else {
            bookRepository.save(book);
            return book;
        }
    }

   //Thêm số lượng sách
    public Book addNumberOfCopy(String bookId, Integer numberOfCopy) throws BadRequestException {
        if (numberOfCopy < 0) {throw new BadRequestException("Số lượng sách không hợp lệ");}
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setNumberOfCopy(book.getNumberOfCopy() + numberOfCopy);
            bookRepository.save(book);
            return book;
        }
        else {throw new InformationNotFoundException("Không tồn sách cần cập nhật");
        }
    }

    //Giảm số lượng sách
    public Book reduceNumberOfCopy(String bookId, Integer numberOfCopy) throws BadRequestException {
        if (numberOfCopy < 0) {throw new BadRequestException("Số lượng sách không hợp lệ");}
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setNumberOfCopy(book.getNumberOfCopy() - numberOfCopy);
            bookRepository.save(book);
            return book;
        }
        else {throw new InformationNotFoundException("Không tồn sách cần cập nhật");
        }
    }
}